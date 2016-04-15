package com.pubnub.api.endpoints.push;

import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.core.Pubnub;
import com.pubnub.api.core.PubnubException;
import com.pubnub.api.core.models.PublishData;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Accessors(chain = true, fluent = true)
public class CreatePushNotification {

    private Pubnub pubnub;
    @Setter private PushType pushType;
    @Setter private Object pushPayload;
    @Setter private String channel;

    public CreatePushNotification(Pubnub pubnub) {
        this.pubnub = pubnub;
    }

    public PublishData sync() throws PubnubException {
        Map<String, Object> payload = preparePayload();
        return pubnub.publish().channel(channel).message(payload).sync();
    }

    public void async(final PNCallback<PublishData> callback) {
        Map<String, Object> payload = preparePayload();
        pubnub.publish().channel(channel).message(payload).async(callback);
    }

    private Map<String, Object> preparePayload() {
        Map<String, Object> payload = new HashMap<>();

        if (pushType == PushType.APNS) {
            payload.put("pn_apns", pushPayload);
        }

        if (pushType == PushType.GCM) {
            payload.put("pn_gcm", pushPayload);
        }

        if (pushType == PushType.MPNS) {
            payload.put("pn_mpns", pushPayload);
        }

        return payload;
    }


}
