package silgar.store.exception;

public class ConflictException extends RuntimeException{

    private static final String DESCRIPTION = "Conflict Exception (409)";
    private String trace = "";

    public ConflictException(String detail){
        super(DESCRIPTION + ". " + detail);
    }

    public ConflictException (String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace){
        super(DESCRIPTION + message, cause, enableSuppression, writableStackTrace);
    }


}
