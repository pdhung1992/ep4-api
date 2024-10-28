package jsb.ep4api.constants;

import java.time.LocalDateTime;

import static java.util.stream.IntStream.rangeClosed;

public class Constants {

    //Roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final long MOVIE_MANAGEMENT_FUNCTION = 1;
    public static final long ACCOUNT_MANAGEMENT_FUNCTION = 6;


    //Default values
    public static final boolean DEFAULT_DELETE_FLAG = false;
    public static final boolean DEFAULT_VERIFY_FLAG = false;
    public static final boolean DEFAULT_SHOW_FLAG = true;
    public static final boolean DEFAULT_IS_USED = false;

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_ORDER = "asc";

    public static final int[] SORT_ORDERS = rangeClosed(1, 20).toArray();

    public static final String[] USER_PACKAGES = {"FREE", "BASIC", "PREMIUM", "VIP"};

    public static final boolean IS_PACKAGE = true;

    public static final String DEFAULT_EXPIRED_TIME = "30 Days";
    public static final LocalDateTime CURRENT_TIME = LocalDateTime.now();
    public static final LocalDateTime EXPIRED_30_DAYS = CURRENT_TIME.plusDays(30);
    public static final LocalDateTime EXPIRED_90_DAYS = CURRENT_TIME.plusDays(90);
    public static final LocalDateTime EXPIRED_180_DAYS = CURRENT_TIME.plusDays(180);

    public static final String DEFAULT_AVATAR = "blank_avatar.png";
    public static final String DEFAULT_POSTER = "blank_poster.png";
    public static final String DEFAULT_BANNER = "default_banner.png";
    public static final String DEFAULT_LOGO = "default_logo.png";
    public static final String DEFAULT_THUMBNAIL = "default_thumbnail.png";

    public static final String DEFAULT_ADMIN_URL = "http://localhost:1234";
    public static final String DEFAULT_USER_URL = "http://localhost:2345";

    public static final String DEFAULT_UPLOAD_IMAGE_DIR = "public/images";
    public static final String DEFAULT_UPLOAD_VIDEO_DIR = "public/media";

    //Patterns
    public static final String PHONE_PATTERN = "^\\d{10}$";
    public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,50}$";
    public static final String SLUG_PATTERN = "^[a-z0-9-]+$";


    //Messages
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized!";
    public static final String FORBIDDEN_MESSAGE = "Forbidden!";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error!";
    public static final String ERROR_OCCURRED_MESSAGE = "An error occurred! Try again later!";

    public static final String REGISTER_SUCCESS_MESSAGE = "User registered successfully!";
    public static final String REGISTER_FAIL_MESSAGE = "Register failed! Error: ";
    public static final String PHONE_EXIST_MESSAGE = "Phone number is already taken!";
    public static final String EMAIL_EXIST_MESSAGE = "Email is already in use!";
    public static final String PASSWORD_NOT_CORRECT_MESSAGE = "Password is not correct!";

    public static final String CREATE_ADMIN_SUCCESS_MESSAGE = "Admin created successfully!";
    public static final String CREATE_ADMIN_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String USERNAME_NOT_FOUND_MESSAGE = "Username not found!";
    public static final String USERNAME_EXIST_MESSAGE = "Username is already taken!";

    public static final String ROLE_NOT_FOUND_MESSAGE = "Role not found!";
    public static final String EMAIL_NOT_FOUND_MESSAGE = "Email not found!";

    public static final String LOGIN_SUCCESS_MESSAGE = "User logged in successfully!";
    public static final String LOGIN_FAIL_MESSAGE = "Username or password is incorrect!";
    public static final String LOGOUT_SUCCESS_MESSAGE = "User logged out successfully!";

    public static final String RESET_PASSWORD_SUCCESS_MESSAGE = "Reset password successfully.";
    public static final String RESET_PASSWORD_TOKEN_ALREADY_SENT_MESSAGE = "Reset password token has already been sent! Please check your email!";

    public static final String USER_NOT_FOUND_MESSAGE = "User not found!";
    public static final String USER_UPDATED_MESSAGE = "User updated successfully!";
    public static final String USER_UPDATE_FAIL_MESSAGE = "Update failed! Error: ";

    public static final String USER_DELETED_MESSAGE = "User deleted successfully!";
    public static final String USER_DELETE_FAIL_MESSAGE = "Delete failed! Error: ";

    public static final String ADMIN_NOT_FOUND_MESSAGE = "Admin not found!";
    public static final String UPDATE_ADMIN_SUCCESS_MESSAGE = "Admin updated successfully!";
    public static final String UPDATE_ADMIN_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_ADMIN_SUCCESS_MESSAGE = "Admin deleted successfully!";
    public static final String DELETE_ADMIN_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String UPDATE_PASSWORD_SUCCESS_MESSAGE = "Password updated successfully!";
    public static final String FUNCTION_NOT_FOUND_MESSAGE = "Function not found!";

    public static final String ROLE_NAME_EXIST_MESSAGE = "Role name is already taken!";
    public static final String ROLE_SLUG_EXIST_MESSAGE = "Role slug is already taken!";
    public static final String CREATE_ROLE_SUCCESS_MESSAGE = "Role created successfully!";
    public static final String CREATE_ROLE_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_ROLE_SUCCESS_MESSAGE = "Role updated successfully!";
    public static final String UPDATE_ROLE_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_ROLE_SUCCESS_MESSAGE = "Role deleted successfully!";
    public static final String DELETE_ROLE_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String UPDATE_ROLE_FUNCTION_SUCCESS_MESSAGE = "Role function updated successfully!";
    public static final String UPDATE_ROLE_FUNCTION_FAIL_MESSAGE = "Update failed! Error: ";

    public static final String CREATE_COUNTRY_SUCCESS_MESSAGE = "Country created successfully!";
    public static final String CREATE_COUNTRY_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_COUNTRY_SUCCESS_MESSAGE = "Country updated successfully!";
    public static final String UPDATE_COUNTRY_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_COUNTRY_SUCCESS_MESSAGE = "Country deleted successfully!";
    public static final String DELETE_COUNTRY_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String COUNTRY_NOT_FOUND_MESSAGE = "Country not found!";

    public static final String CREATE_STUDIO_SUCCESS_MESSAGE = "Studio created successfully!";
    public static final String CREATE_STUDIO_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_STUDIO_SUCCESS_MESSAGE = "Studio updated successfully!";
    public static final String UPDATE_STUDIO_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_STUDIO_SUCCESS_MESSAGE = "Studio deleted successfully!";
    public static final String DELETE_STUDIO_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String STUDIO_NOT_FOUND_MESSAGE = "Studio not found!";

    public static final String CREATE_PACKAGE_SUCCESS_MESSAGE = "Package created successfully!";
    public static final String CREATE_PACKAGE_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_PACKAGE_SUCCESS_MESSAGE = "Package updated successfully!";
    public static final String UPDATE_PACKAGE_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_PACKAGE_SUCCESS_MESSAGE = "Package deleted successfully!";
    public static final String DELETE_PACKAGE_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String PACKAGE_NOT_FOUND_MESSAGE = "Package not found!";

    public static final String CREATE_CLASSIFICATION_SUCCESS_MESSAGE = "Classification created successfully!";
    public static final String CREATE_CLASSIFICATION_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_CLASSIFICATION_SUCCESS_MESSAGE = "Classification updated successfully!";
    public static final String UPDATE_CLASSIFICATION_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_CLASSIFICATION_SUCCESS_MESSAGE = "Classification deleted successfully!";
    public static final String DELETE_CLASSIFICATION_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String CLASSIFICATION_NOT_FOUND_MESSAGE = "Classification not found!";

    public static final String CREATE_CATEGORY_SUCCESS_MESSAGE = "Category created successfully!";
    public static final String CREATE_CATEGORY_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_CATEGORY_SUCCESS_MESSAGE = "Category updated successfully!";
    public static final String UPDATE_CATEGORY_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_CATEGORY_SUCCESS_MESSAGE = "Category deleted successfully!";
    public static final String DELETE_CATEGORY_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found!";

    public static final String CREATE_GENRE_SUCCESS_MESSAGE = "Genre created successfully!";
    public static final String CREATE_GENRE_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_GENRE_SUCCESS_MESSAGE = "Genre updated successfully!";
    public static final String UPDATE_GENRE_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_GENRE_SUCCESS_MESSAGE = "Genre deleted successfully!";
    public static final String DELETE_GENRE_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String GENRE_NOT_FOUND_MESSAGE = "Genre not found!";

    public static final String CREATE_LANGUAGE_SUCCESS_MESSAGE = "Language created successfully!";
    public static final String CREATE_LANGUAGE_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_LANGUAGE_SUCCESS_MESSAGE = "Language updated successfully!";
    public static final String UPDATE_LANGUAGE_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_LANGUAGE_SUCCESS_MESSAGE = "Language deleted successfully!";
    public static final String DELETE_LANGUAGE_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String LANGUAGE_NOT_FOUND_MESSAGE = "Language not found!";

    public static final String CREATE_VIDEO_MODE_SUCCESS_MESSAGE = "Video mode created successfully!";
    public static final String CREATE_VIDEO_MODE_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_VIDEO_MODE_SUCCESS_MESSAGE = "Video mode updated successfully!";
    public static final String UPDATE_VIDEO_MODE_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_VIDEO_MODE_SUCCESS_MESSAGE = "Video mode deleted successfully!";
    public static final String DELETE_VIDEO_MODE_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String VIDEO_MODE_NOT_FOUND_MESSAGE = "Video mode not found!";

    public static final String CREATE_MOVIE_SUCCESS_MESSAGE = "Movie created successfully!";
    public static final String CREATE_MOVIE_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_MOVIE_SUCCESS_MESSAGE = "Movie updated successfully!";
    public static final String UPDATE_MOVIE_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_MOVIE_SUCCESS_MESSAGE = "Movie deleted successfully!";
    public static final String DELETE_MOVIE_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String MOVIE_NOT_FOUND_MESSAGE = "Movie not found!";
    public static final String CAN_NOT_WATCH_MESSAGE = "You can not watch this movie!";

    public static final String CREATE_MOVIE_FILE_SUCCESS_MESSAGE = "Movie file created successfully!";
    public static final String CREATE_MOVIE_FILE_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_MOVIE_FILE_SUCCESS_MESSAGE = "Movie file updated successfully!";
    public static final String UPDATE_MOVIE_FILE_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_MOVIE_FILE_SUCCESS_MESSAGE = "Movie file deleted successfully!";
    public static final String DELETE_MOVIE_FILE_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String MOVIE_FILE_NOT_FOUND_MESSAGE = "Movie file not found!";

    public static final String CREATE_CAST_SUCCESS_MESSAGE = "Cast created successfully!";
    public static final String CREATE_CAST_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_CAST_SUCCESS_MESSAGE = "Cast updated successfully!";
    public static final String UPDATE_CAST_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_CAST_SUCCESS_MESSAGE = "Cast deleted successfully!";
    public static final String DELETE_CAST_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String CAST_NOT_FOUND_MESSAGE = "Cast not found!";

    public static final String CREATE_CREW_MEMBER_SUCCESS_MESSAGE = "Crew member created successfully!";
    public static final String CREATE_CREW_MEMBER_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_CREW_MEMBER_SUCCESS_MESSAGE = "Crew member updated successfully!";
    public static final String UPDATE_CREW_MEMBER_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_CREW_MEMBER_SUCCESS_MESSAGE = "Crew member deleted successfully!";
    public static final String DELETE_CREW_MEMBER_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String CREW_MEMBER_NOT_FOUND_MESSAGE = "Crew member not found!";

    public static final String CREATE_CREW_POSITION_SUCCESS_MESSAGE = "Crew position created successfully!";
    public static final String CREATE_CREW_POSITION_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_CREW_POSITION_SUCCESS_MESSAGE = "Crew position updated successfully!";
    public static final String UPDATE_CREW_POSITION_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_CREW_POSITION_SUCCESS_MESSAGE = "Crew position deleted successfully!";
    public static final String DELETE_CREW_POSITION_FAIL_MESSAGE = "Delete failed! Error: ";
    public static final String CREW_POSITION_NOT_FOUND_MESSAGE = "Crew position not found!";

    public static final String CREATE_MOVIE_SUCCESS = "Movie created successfully!";
    public static final String CREATE_MOVIE_FAIL = "Create failed! Error: ";
    public static final String UPDATE_MOVIE_SUCCESS = "Movie updated successfully!";
    public static final String UPDATE_MOVIE_FAIL = "Update failed! Error: ";
    public static final String DELETE_MOVIE_SUCCESS = "Movie deleted successfully!";
    public static final String DELETE_MOVIE_FAIL = "Delete failed! Error: ";
    public static final String MOVIE_NOT_FOUND = "Movie not found!";

    public static final String CREATE_REVIEW_SUCCESS = "Review created successfully!";
    public static final String CREATE_REVIEW_FAIL = "Create failed! Error: ";
    public static final String UPDATE_REVIEW_SUCCESS = "Review updated successfully!";
    public static final String UPDATE_REVIEW_FAIL = "Update failed! Error: ";
    public static final String DELETE_REVIEW_SUCCESS = "Review deleted successfully!";
    public static final String DELETE_REVIEW_FAIL = "Delete failed! Error: ";
    public static final String REVIEW_NOT_FOUND = "Review not found!";

    public static final String CREATE_REVIEW_REACTION_SUCCESS = "Review reaction created successfully!";
    public static final String CREATE_REVIEW_REACTION_FAIL = "Create failed! Error: ";
    public static final String UPDATE_REVIEW_REACTION_SUCCESS = "Review reaction updated successfully!";
    public static final String UPDATE_REVIEW_REACTION_FAIL = "Update failed! Error: ";
    public static final String DELETE_REVIEW_REACTION_SUCCESS = "Review reaction deleted successfully!";
    public static final String DELETE_REVIEW_REACTION_FAIL = "Delete failed! Error: ";
    public static final String REVIEW_REACTION_NOT_FOUND = "Review reaction not found!";
    public static final boolean REACTION_TYPE_LIKE = true;
    public static final boolean REACTION_TYPE_DISLIKE = false;

    //Email
    public static final String REGISTER_SUCCESS_SUBJECT = "MovieX - Register Success Confirmation";
    public static final String REGISTER_SUCCESS_BODY = "<strong>Hello %s!</strong>" +
            "<p>Thank you for registering on our website. We hope you enjoy our services.</p>" +
            "<p>Best regards,</p>" +
            "<p>MovieX Team.</p>";

    public static final String RESET_PASSWORD_SUBJECT = "MovieX - Reset Password Confirmation";
    public static final String RESET_PASSWORD_BODY = "<strong>Hello %s!</strong>" +
            "<p>You have requested to reset your password. Please click the link below to reset your password.</p>" +
            "<a href='%s/reset-password?token=%s'>Reset Password</a>" +
            "<p>If you did not request to reset your password, please ignore this email.</p>" +
            "<p>Best regards,</p>" +
            "<p>MovieX Team.</p>";

    //Payment
    public static final String PAYMENT_METHOD_PAYPAL = "PayPal";
    public static final String PAYMENT_METHOD_VNPAY = "VNPay";

    public static final int PAYMENT_STATUS_PENDING = 0;
    public static final String PAYMENT_PENDING_MESSAGE = "Payment pending!";

    public static final int PAYMENT_STATUS_SUCCESS = 1;
    public static final String PAYMENT_SUCCESS_MESSAGE = "Payment success!";

    public static final int PAYMENT_STATUS_FAIL = 2;
    public static final String PAYMENT_FAIL_MESSAGE = "Payment failed!";

    public static final int PAYMENT_STATUS_CANCEL = 3;
    public static final String PAYMENT_CANCEL_MESSAGE = "Payment canceled!";

    public static final int PAYMENT_STATUS_ERROR = 4;
    public static final String PAYMENT_ERROR_MESSAGE = "Payment error!";

    public static final int PAYMENT_SIGNATURE_INVALID = 5;
    public static final String INVALID_SIGNATURE_MESSAGE = "Invalid signature!";

    public static final String REDIRECT_URL_PAYMENT_SUCCESS = "http://localhost:1234/payment/success";
    public static final String REDIRECT_URL_PAYMENT_FAIL = "http://localhost:1234/payment/fail";

    public static final String TRANSACTION_NOT_FOUND_MESSAGE = "Transaction not found!";


    //VNPay
    public static final String VN_PAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String VN_PAY_TMN_CODE = "7X7BO2T6";
    public static final String VN_PAY_HASH_SECRET = "MTWMTCP6NKI48PLU0DE30591RD1NDMPT";
    public static final String VN_PAY_RETURN_URL = "http://localhost:3000/loading";
    public static final String VN_PAY_API_URL = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
    public static final String VN_PAY_VERSION = "2.1.0";
    public static final String VN_PAY_COMMAND = "pay";
    public static final String VN_PAY_ORDER_TYPE = "other";
    public static final String VN_PAY_CURRENCY_CODE = "VND";
    public static final String VN_PAY_LOCATE = "vn";

    public static final String VN_PAY_STATUS_CODE_SUCCESS = "00";

    //Exchange rate API
    public static final String EXCHANGE_RATE_API_URL = "https://v6.exchangerate-api.com/v6/e1bb810e23034bfea8bbd553/latest/USD";

    private Constants() {
        super();
    }
}
