package ru.norgorn.moneySync.web.requestHandler;

import java.util.Map;

public interface RequestHandler<V extends Validable> {

    Answer process(V value, Map<String, String> urlParams, boolean shouldReturnHtml);
}