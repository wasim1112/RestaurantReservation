package app.iam.user.enumeration;


public enum UserStatus {
    EMAIL_UNCONFIRMED(1),
    MOBILE_UNCONFIRMED(2),
    CONFIRMED(3);

    public final int stepNumber;
    UserStatus(int stepNumber){
        this.stepNumber = stepNumber;
    }
}
