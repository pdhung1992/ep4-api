package jsb.ep4api.controllers;

import jsb.ep4api.entities.Transaction;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.payloads.responses.TransactionResponse;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/user")
    public ResponseEntity<?> getTransactionsByUser(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir
    ) {
        try {
            if (pageNo == null) {
                pageNo = 1;
            }
            if (pageSize == null) {
                pageSize = 10;
            }
            if (sortField == null) {
                sortField = "createdAt";
            }
            if (sortDir == null) {
                sortDir = "desc";
            }

            UserDetailsImp userDetailsImp = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = userDetailsImp.getId();

            Page<Transaction> transactions = transactionService.getTransactionsByUser(userId, pageNo, pageSize, sortField, sortDir);
            List<Transaction> transactionList = transactions.getContent();
            List<TransactionResponse> responses = new ArrayList<>();

            if (!transactionList.isEmpty()) {
                for (Transaction transaction : transactionList) {
                    TransactionResponse response = new TransactionResponse();
                    response.setId(transaction.getId());
                    response.setCode(transaction.getCode());
                    response.setAmount(transaction.getAmount());
                    response.setContent(transaction.getContent());
                    response.setGateway(transaction.getGateway());
                    response.setStatus(transaction.getStatus());
                    response.setUserId(transaction.getUser().getId());
                    response.setIsPackage(transaction.getIsPackage());
                    if (transaction.getAPackage() != null) {
                        response.setPackageId(transaction.getAPackage().getId());
                        response.setPackageName(transaction.getAPackage().getPackageName());
                    } else {
                        response.setPackageId(null);
                        response.setPackageName("Unknown Package");
                    }

                    if (transaction.getMovie() != null) {
                        response.setMovieId(transaction.getMovie().getId());
                        response.setMovieTitle(transaction.getMovie().getTitle());
                    } else {
                        response.setMovieId(null);
                        response.setMovieTitle("Unknown Movie");
                    }
                    response.setCreatedAt(transaction.getCreatedAt());

                    responses.add(response);
                }
            }

            int totalPages = transactions.getTotalPages();
            long totalItems = transactions.getTotalElements();
            long fromItem = (pageNo - 1) * pageSize + 1;
            long toItem = 0;
            if (totalItems > pageSize) {
                toItem = fromItem + pageSize - 1;
            } else {
                toItem = totalItems;
            }

            SpecResponse specResponse = new SpecResponse();
            specResponse.setCurrentPage(pageNo);
            specResponse.setTotalPages(totalPages);
            specResponse.setTotalItems(totalItems);
            specResponse.setFromItem(fromItem);
            specResponse.setToItem(toItem);
            specResponse.setPageSize(pageSize);
            specResponse.setSortField(sortField);
            specResponse.setSortDir(sortDir);
            specResponse.setData(responses);

            return ResponseEntity.ok(specResponse);


        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
