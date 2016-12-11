package melerospaw.memoryutil.validation;

import java.util.HashMap;

import melerospaw.memoryutil.validation.ValidationEnums.Invalidity;
import melerospaw.memoryutil.validation.ValidationEnums.Method;
import melerospaw.memoryutil.validation.ValidationEnums.Parameter;

public interface ValidationInfoInterface {
    
    Method getMethod();
    Parameter getInvalidParameter();
    void setInvalidParameter(Parameter parameter);
    Invalidity getInvalidityType();
    void setInvalidityType(Invalidity invalidityType);
    boolean isValid();
    void setIsValid(boolean isValid);
    void setUnexpectedParamInfo();
    HashMap<Parameter, Object> getParameterList();
    String getParameterListToString();
    
    
}
