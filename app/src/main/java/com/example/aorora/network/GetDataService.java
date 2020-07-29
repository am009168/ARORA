package com.example.aorora.network;

import com.example.aorora.model.Butterfly;
import com.example.aorora.model.ButterflyLike;
import com.example.aorora.model.ButterflyLikeCreateReturn;
import com.example.aorora.model.DailyTask;
import com.example.aorora.model.DailyTaskReturn;
import com.example.aorora.model.MoodReportIdReturn;
import com.example.aorora.model.NotificationCreateReturn;
import com.example.aorora.model.QuesrtReportCreateReturn;
import com.example.aorora.model.Quest;
import com.example.aorora.model.QuestReport;
import com.example.aorora.model.RetroPhoto;
import com.example.aorora.model.UserAuth;
import com.example.aorora.model.UserIdReturn;
import com.example.aorora.model.UserInfo;
import com.example.aorora.model.UserInteraction;
import com.example.aorora.model.Notification;
import com.example.aorora.model.UserInteractionCreateReturn;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/photos")
    Call<List<RetroPhoto>> getAllPhotos();

    @GET("/butterflies?format=json")
    Call<List<Butterfly>> getButterflyInfo();

    @POST("/notification")
    @FormUrlEncoded
    Call<NotificationCreateReturn> createLike(@Field("initiator_user_id") Integer sender,
                                              @Field("receiver_user_id") Integer receiver,
                                              @Field("user_interaction_type_id") Integer interaction_type_id,
                                              @Field("quest_report_id") Integer quest_report_id,
                                              @Field("user_interaction_content") String content);
    /*
    @DELETE("/butterflylike/{butterfly_like_id}/")
    Call<Void> removeLike(@Path("butterfly_like_id") Integer butterfly_like_id);
    */

    @GET("/butterflylikes")
    Call<List<ButterflyLike>> getAllLikes();


    @POST("/butterflies")
    Call<Butterfly> createButterfly(@Field("butterfly_id") Integer butterfly_id,
                                    @Field("user_id") Integer user_id);

    @POST("/userbutterfly")
    Call<Butterfly> createButterfly(@Body Butterfly user);


    @POST("/moodreport")
    @FormUrlEncoded
    Call<MoodReportIdReturn> createMoodReport(@Field("user_id") Integer user_id,
                                              @Field("q1_response") Integer q1_response,
                                              @Field("q1_response") Integer q2_response);

    // WORKS

    @POST("/api-token-auth")
    @FormUrlEncoded
    Call<UserAuth> login(@Field("username") String username, @Field("password") String password);

    @POST("/userinteraction")
    @FormUrlEncoded
    Call<UserInteraction> userInteract(@Field("initiator_user_id") Integer sender,
                                       @Field("receiver_user_id") Integer receiver,
                                       @Field("user_interaction_type_id") Integer interaction_type_id,
                                       @Field("quest_report_id") Integer quest_report_id,
                                       @Field("user_interaction_content") String content);



    //Uses server side filtering to get the notifications for the specific user and public notifications
    @GET("/userinteraction_get_notif/{receiver_user_id}")
    Call<List<Notification>> getAllNotifications(@Path("receiver_user_id") Integer receiver_user_id);

    //Retrieves the user interactions that are visible notifications (which are not likes)
    @GET("/userinteraction_get_vis/{receiver_user_id}")
    Call<List<Notification>> getVisibleNotifs(@Path("receiver_user_id") Integer receiver_user_id);


    @GET("/notification/{notification_id}")
    Call<Notification> getNotificationTypeById(@Path("notification_id") Integer notification_id);

    //A UserInteraction with a type of 3 is a like, but we only want the like that the user has done.
    @GET("/userinteraction/{initiator_user_id}")
    Call<List<Notification>> getUserLikes(@Path("initiator_user_id") Integer user_id);

    @GET("/questreports")
    Call<List<QuestReport>> getAllQuestsInCommunity();

    @GET("/quest/{quest_id}")
    Call<Quest> getQuestInfo(@Path("quest_id") Integer quest_id);

    @GET("/userinfo/{user_id}")
    Call<UserInfo> getUserInfo(@Path("user_id") Integer user_id);

    @PUT("/userinfo/{user_id}")
    @FormUrlEncoded
    Call<UserIdReturn> updateUserCurrentButterfly(@Path("user_id") Integer user_id,
                                    @Field("user_current_butterfly") Integer user_current_butterfly);

    @PUT("/userinfo/{user_id}")
    @FormUrlEncoded
    Call<UserIdReturn> updateUserPollen(@Path("user_id") Integer user_id,
                                        @Field("user_pollen") Integer user_pollen);
    @GET("/userinfos")
    Call<List<UserInfo>> getCommunity();

    @GET("/dailytask/{user_id}")
    Call<DailyTask> getDailyTask(@Path("user_id") Integer user_id);

    @PUT("/dailytask/{user_id}")
    @FormUrlEncoded
    Call<DailyTaskReturn> updateDailyTaskM1(@Path("user_id") Integer user_id,
                                            @Field("daily_task_user_id") Integer daily_task_user_id,
                                            @Field("daily_task_m1_achieved") Integer daily_task_m1_achieved);

    @PUT("/dailytask/{user_id}")
    @FormUrlEncoded
    Call<DailyTaskReturn> updateDailyTaskM2(@Path("user_id") Integer user_id,
                           @Field("daily_task_user_id") Integer daily_task_user_id,
                           @Field("daily_task_m2_achieved") Integer daily_task_m2_achieved);

    @PUT("/dailytask/{user_id}")
    @FormUrlEncoded
    Call<DailyTaskReturn> updateDailyTaskM3(@Path("user_id") Integer user_id,
                           @Field("daily_task_user_id") Integer daily_task_user_id,
                           @Field("daily_task_m3_achieved") Integer daily_task_m3_achieved);
    @POST("/questreport")
    @FormUrlEncoded
    Call<QuesrtReportCreateReturn> createQuestReport(@Field("quest_id") Integer quest_id,
                                                     @Field("user_id") Integer user_id);
}