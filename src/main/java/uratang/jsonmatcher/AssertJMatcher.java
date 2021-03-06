package uratang.jsonmatcher;

import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;

public class AssertJMatcher extends AbstractAssert<AssertJMatcher, Response> {
    private final int statusCode;
    private final JsonData actual;

    public AssertJMatcher(Response response) {
        super(response, AssertJMatcher.class);
        this.statusCode = response.getStatusCode();
        this.actual = JsonMatcher.actual(response.getBody().asString());
    }

    public static AssertJMatcher assertThat(Response response) {
        return new AssertJMatcher(response);
    }

    public AssertJMatcher match(String expectedJson) {
        JsonData expected = JsonMatcher.expected(expectedJson);
        if(expected instanceof JsonObject && actual instanceof  JsonObject){
            JsonObject e = (JsonObject) expected;
            JsonObject a = (JsonObject) actual;
            if (!a.entrySet().containsAll(e.entrySet())) {
                failWithMessage("");
            }
        }

        if(expected instanceof JsonArray && actual instanceof  JsonArray){
            JsonArray e = (JsonArray) expected;
            JsonArray a = (JsonArray) actual;
            if(!a.containsAll(e)) {
                failWithMessage("");
            }
        }

        return this;
    }

    public AssertJMatcher statusCode(int statusCode){
        if(this.statusCode != statusCode){
            failWithMessage("");
        }
        return this;

    }
}