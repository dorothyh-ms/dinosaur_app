package be.kdg.dinosaurs.domain;

public enum UserRole {
    USER("ROLE_USER"), MODERATOR("ROLE_MODERATOR"), ADMIN("ROLE_ADMIN");
    private final String code;


    public String getCode() {
        return code;
    }

    UserRole(String code) {
        this.code = code;
    }
}

