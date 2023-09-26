package _shared.token;

import es.grouppayments.backend._shared.domain.TokenService;

import java.util.Date;
import java.util.Map;

public final class FakeTokenService implements TokenService {
    protected static String TOKEN_TEMPLATE = "token-%s";

    private boolean willBeExpired;
    private String getOhterWillReturn;
    private String otherWillReturn;

    @Override
    public String generateToken(String body, Map<String, Object> other, Date expiration) {
        return String.format(TOKEN_TEMPLATE, body);
    }

    @Override
    public String generateToken(String body, Date expiration) {
        return String.format(TOKEN_TEMPLATE, body);
    }

    @Override
    public String getBody(String token) {
        return this.otherWillReturn == null || this.otherWillReturn.equals("") ?
                token :
                this.otherWillReturn;
    }

    @Override
    public String getOther(String token, String key) {
        return this.getOhterWillReturn == null || this.getOhterWillReturn.equals("") ?
                token :
                this.getOhterWillReturn;
    }

    @Override
    public boolean isBodyValid(String token, String expectedBody) {
        return true;
    }

    @Override
    public boolean hasBody(String token) {
        return true;
    }

    @Override
    public boolean isExpired(String token) {
        if(this.willBeExpired){
            this.willBeExpired = false;

            return true;
        }

        return false;
    }

    public void willBeExpired(){
        this.willBeExpired = true;
    }

    public void getOtherWillReturn(String toReturn){
        this.getOhterWillReturn = toReturn;
    }

    public void getBodyWillReturn(String otherWillReturn){
        this.otherWillReturn = otherWillReturn;
    }
}
