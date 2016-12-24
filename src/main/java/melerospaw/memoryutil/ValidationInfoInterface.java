package melerospaw.memoryutil;

import java.util.HashMap;

import melerospaw.memoryutil.ValidationEnums.Invalidity;
import melerospaw.memoryutil.ValidationEnums.Method;
import melerospaw.memoryutil.ValidationEnums.Parameter;

interface ValidationInfoInterface {
    
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
