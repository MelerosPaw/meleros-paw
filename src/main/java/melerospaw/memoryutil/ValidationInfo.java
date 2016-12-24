package melerospaw.memoryutil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import melerospaw.memoryutil.ValidationEnums.Invalidity;
import melerospaw.memoryutil.ValidationEnums.Method;
import melerospaw.memoryutil.ValidationEnums.Parameter;

class ValidationInfo implements ValidationInfoInterface{
    
    private final Method method;
    private final HashMap<Parameter, Object> parameters;
    private Parameter invalidParameter;
    private Invalidity invalidityType;
    private boolean isValid;

    public ValidationInfo(Method method, HashMap<Parameter, Object> parameters) {
        this.method = method;
        this.parameters = parameters;
    }

    @Override
    public void setInvalidParameter(Parameter invalidParameter) {
        this.invalidParameter = invalidParameter;
    }

    @Override
    public void setInvalidityType(Invalidity invalidityType) {
        this.invalidityType = invalidityType;
    }

    @Override
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
    
    @Override
    public Parameter getInvalidParameter(){
        return invalidParameter;
    }
    
    @Override
    public Invalidity getInvalidityType(){
        return invalidityType;
    }    
   
    @Override
    public boolean isValid(){return isValid;}
    
    public Method getMethod(){
        return method;
    }

    @Override
    public HashMap<Parameter, Object> getParameterList() {
        return parameters;
    }    

    @Override
    public String getParameterListToString(){
        String parameterList = "";
        Iterator<Map.Entry<Parameter, Object>> it = getParameterList().entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Parameter, Object> entry = it.next();
            String value = processValue(entry.getValue());
            parameterList += "\t" + entry.getKey().parameter + ": " + value;
            if (it.hasNext()){
                parameterList +="\n";
            }
        }
        
        return parameterList;
    }

    public void fillParameters(Parameter parameter, Invalidity invalidity, boolean isValid){
        setInvalidParameter(parameter);
        setInvalidityType(invalidity);
        setIsValid(isValid);
    }
    
    public void setUnexpectedParamInfo(){
        fillParameters(Parameter.UNEXPECTED_PARAMETER, Invalidity.UNEXPECTED, false);
    }

    public String processValue(Object value){

        String valueProcessed;

        if (value == null){
            valueProcessed = "null";
        } else if (value instanceof String){
           valueProcessed = (String) value;
        } else {
            valueProcessed = value.toString();
        }

        if (valueProcessed.isEmpty()){
            valueProcessed = "<empty>";
        }

        return valueProcessed;
    }
}
