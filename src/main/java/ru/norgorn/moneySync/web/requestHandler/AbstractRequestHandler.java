package ru.norgorn.moneySync.web.requestHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import spark.Request;
import spark.Response;
import spark.Route;

public abstract class AbstractRequestHandler<V extends Validable> 
		implements RequestHandler<V>, Route {

//    private Class<V> valueClass;
//
//    protected static final int HTTP_BAD_REQUEST = 400;
//
//    public AbstractRequestHandler(Class<V> valueClass){
//        this.valueClass = valueClass;
//    }
//
//    private static boolean shouldReturnHtml(Request request) {
//        String accept = request.headers("Accept");
//        return accept != null && accept.contains("text/html");
//    }
//
////    public static String dataToJson(Object data) {
////        try {
////            ObjectMapper mapper = new ObjectMapper();
////            mapper.enable(SerializationFeature.INDENT_OUTPUT);
////            StringWriter sw = new StringWriter();
////            mapper.writeValue(sw, data);
////            return sw.toString();
////        } catch (IOException e){
////            throw new RuntimeException("IOException from a StringWriter?",e);
////        }
////    }
//
//    protected abstract Answer processImpl(V value, Map<String, String> queryParams);
//
//
//    @Override
//    public Object handle(Request request, Response response) throws Exception {
//        Optional<V> valueOptional = tryBodyToJson(request);
//        
//        Answer answer;
//        if(valueOptional.isPresent() 
//        		&& valueOptional.get().isValid()){
//        	V value = valueOptional.get();
//        
//        	Map<String, String> queryParams = new HashMap<>();
//        
//        	answer = processImpl(value, queryParams);
//        }
//        else 
//        	answer = new Answer(HTTP_BAD_REQUEST);
//        
//        setupResponse(request, response, answer);
//        
//        return answer.getBody();
//    }
//
//	private Optional<V> tryBodyToJson(Request request) {
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//	        V value = objectMapper.readValue(request.body(), valueClass);
//			return Optional.of(value);
//		} catch (Exception e) {
//			return Optional.empty();
//		}
//	}
//	
//	private void setupResponse(Request request, Response response, Answer answer) {
//		response.status(answer.getCode());
//        response.body(answer.getBody());
//        setResponseType(request, response);
//	}
//	
//	private void setResponseType(Request request, Response response) {
//		if (shouldReturnHtml(request)) {
//            response.type("text/html");
//        } else {
//            response.type("application/json");
//        }
//	}
}
