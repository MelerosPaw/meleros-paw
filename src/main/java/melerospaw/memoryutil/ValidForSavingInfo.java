package melerospaw.memoryutil;

/**
 * Created by Juan José Melero on 16/09/2016.
 */
public class ValidForSavingInfo implements ValidForSavingInfoInterface {

    private Invalidity reason;
    private boolean isValid;
    private @InvalidParameterException.InvalidParameter String parameter;

    public ValidForSavingInfo(@InvalidParameterException.InvalidParameter String parameter,
                              boolean isValid, Invalidity reason){
        this.parameter = parameter;
        this.reason = reason;
        this.isValid = isValid;
    }

    @Override
    public Invalidity getReason() {
        return reason;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public @InvalidParameterException.InvalidParameter String getInvalidParameter(){
        return parameter;
    }
}
