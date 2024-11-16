package jsb.ep4api.third_parties.vnpay;

import jakarta.servlet.http.HttpServletRequest;
import jsb.ep4api.entities.*;
import jsb.ep4api.entities.Package;
import jsb.ep4api.payloads.requests.TransactionRequest;
import jsb.ep4api.payloads.requests.VNPayRequest;
import jsb.ep4api.payloads.responses.RequestResponse;
import jsb.ep4api.payloads.responses.VNPayResponse;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/payment/vnpay")
public class VNPayPaymentController {
    @Autowired
    private CurrencyExchangeService currencyExchangeService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    UserMovieService userMovieService;
    @Autowired
    UserPackageService userPackageService;
    @Autowired
    UserService userService;

    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(
            @RequestBody VNPayRequest request,
            HttpServletRequest servletRequest
    ) {
        try {
            UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = userDetails.getId();

            Double amount = request.getAmount();
            long amountVND = currencyExchangeService.convertUsdToVnd(BigDecimal.valueOf(amount)).longValue()*100;
            String bankCode = request.getBankCode();

            String vnp_TxnRef = Config.getRandomNumber(8);
            String vnp_IpAddr = Config.getIpAddress(servletRequest);

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", VN_PAY_VERSION);
            vnp_Params.put("vnp_Command", VN_PAY_COMMAND);
            vnp_Params.put("vnp_TmnCode", VN_PAY_TMN_CODE);
            vnp_Params.put("vnp_Amount", String.valueOf(amountVND));
            vnp_Params.put("vnp_CurrCode", VN_PAY_CURRENCY_CODE);

            if (bankCode != null && !bankCode.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bankCode);
            }
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Payment for order:" + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", VN_PAY_ORDER_TYPE);

            String locate = request.getLocale();
            if (locate != null && !locate.isEmpty()) {
                vnp_Params.put("vnp_Locale", locate);
            } else {
                vnp_Params.put("vnp_Locale", VN_PAY_LOCATE);
            }
            vnp_Params.put("vnp_ReturnUrl", VN_PAY_RETURN_URL);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = Config.hmacSHA512(VN_PAY_HASH_SECRET, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VN_PAY_URL + "?" + queryUrl;

            VNPayResponse response = new VNPayResponse();
            response.setStatus("Ok");
            response.setMessage("Success");
            response.setUrl(paymentUrl);

            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest.setCode(vnp_TxnRef);
            transactionRequest.setAmount(request.getAmount());
            transactionRequest.setContent("Payment for order: " + vnp_TxnRef + " via VNPay");
            transactionRequest.setGateway(PAYMENT_METHOD_VNPAY);
            transactionRequest.setIsPackage(request.getIsPackage());
            transactionRequest.setUserId(userId);
            transactionRequest.setPackageId(request.getPackageId());
            transactionRequest.setMovieId(request.getMovieId());

            transactionService.createTransaction(transactionRequest);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ipn")
    public ResponseEntity<?> returnIPN(HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("fuck");
            }

            UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you");
            }
            Long userId = userDetails.getId();
            User user = userService.findById(userId);


            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType"))
            {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash"))
            {
                fields.remove("vnp_SecureHash");
            }

            String signValue = Config.hashAllFields(fields);

            if (signValue.equals(vnp_SecureHash)) {
                String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
                if (vnp_ResponseCode.equals(VN_PAY_STATUS_CODE_SUCCESS)) {
                    //Handle success payment here
                    String code = request.getParameter("vnp_TxnRef");
                    Transaction transaction = transactionService.findByCode(code);
                    if (transaction == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TRANSACTION_NOT_FOUND_MESSAGE);
                    }

                    //Update transaction status
                    int status = PAYMENT_STATUS_SUCCESS;
                    transactionService.updateTransactionStatus(code, status);

                    //Update user's package or movie
                    if (transaction.getIsPackage()) {
                        //Update user's package
                        userPackageService.updateUserPackageByTransaction(transaction, user);
                    } else {
                        //Update user's movie
                        userMovieService.updateUserMovieByTransaction(transaction, user);
                    }

                    //Return success message
                    return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                            HttpStatus.OK.value(),
                            PAYMENT_SUCCESS_MESSAGE
                    ));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PAYMENT_FAIL_MESSAGE);
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_SIGNATURE_MESSAGE);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
