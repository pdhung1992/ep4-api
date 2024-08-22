package jsb.ep4api.constrants;

import java.time.LocalDateTime;

import static java.util.stream.IntStream.rangeClosed;

public class Constants {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    public static final boolean DEFAULT_DELETE_FLAG = false;
    public static final boolean DEFAULT_VERIFY_FLAG = false;

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

    public static final String LOGIN_SUCCESS_MESSAGE = "User logged in successfully!";
    public static final String LOGIN_FAIL_MESSAGE = "Username or password is incorrect!";
    public static final String LOGOUT_SUCCESS_MESSAGE = "User logged out successfully!";

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

    public static final String ROLE_NAME_EXIST_MESSAGE = "Role name is already taken!";
    public static final String ROLE_SLUG_EXIST_MESSAGE = "Role slug is already taken!";
    public static final String CREATE_ROLE_SUCCESS_MESSAGE = "Role created successfully!";
    public static final String CREATE_ROLE_FAIL_MESSAGE = "Create failed! Error: ";
    public static final String UPDATE_ROLE_SUCCESS_MESSAGE = "Role updated successfully!";
    public static final String UPDATE_ROLE_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String DELETE_ROLE_SUCCESS_MESSAGE = "Role deleted successfully!";
    public static final String DELETE_ROLE_FAIL_MESSAGE = "Delete failed! Error: ";


    private Constants() {
        super();
    }
}
