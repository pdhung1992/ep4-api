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

    public static final String DEFAULT_AVATAR = "blank_avatar.png";
    public static final String DEFAULT_POSTER = "blank_poster.png";
    public static final String DEFAULT_BANNER = "default_banner.png";
    public static final String DEFAULT_LOGO = "default_logo.png";

    public static final String DEFAULT_ADMIN_URL = "http://localhost:1234";
    public static final String DEFAULT_USER_URL = "http://localhost:2345";

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


    private Constants() {
        super();
    }
}
