package edu.northwestern.cbits.xsi.oauth;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class LinkedInApi extends org.scribe.builder.api.LinkedInApi
{
	public static final String CONSUMER_KEY = "linkedin_consumer_key";
	public static final String CONSUMER_SECRET = "linkedin_consumer_secret";
    public static final String USER_TOKEN = "linkedin_user_token";
    public static final String USER_SECRET = "linkedin_user_secret";

    public static JSONObject fetch(String url)
    {
        Token accessToken = new Token(Keystore.get(LinkedInApi.USER_TOKEN), Keystore.get(LinkedInApi.USER_SECRET));

        ServiceBuilder builder = new ServiceBuilder();
        builder = builder.provider(org.scribe.builder.api.LinkedInApi.class);
        builder = builder.apiKey(Keystore.get(LinkedInApi.CONSUMER_KEY));
        builder = builder.apiSecret(Keystore.get(LinkedInApi.CONSUMER_SECRET));

        final OAuthService service = builder.build();

        final OAuthRequest request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);

        Response response = request.send();

        try
        {
            String body = response.getBody();

            return new JSONObject(body);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}