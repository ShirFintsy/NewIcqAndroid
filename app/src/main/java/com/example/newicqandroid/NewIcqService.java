package com.example.newicqandroid;

import static java.lang.Integer.parseInt;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.infoForIntent;
import com.example.newicqandroid.repositories.ChatRepository;
import com.example.newicqandroid.repositories.MessagesRepository;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class NewIcqService extends FirebaseMessagingService {
    private MessagesRepository msgRepo;
    private ChatRepository chatRepo;

    public NewIcqService() {
    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() != null){
           createNotificationChannel();

           //get info from server (message details):
            Map<String, String> data = remoteMessage.getData();
            String toUser = data.get("toUser");
            String fromUser = data.get("fromUser");
            //String chatId = data.get("chatId");
            String msg = data.get("text");
            String cpyMsg = new String(msg);


            /*chatRepo = new ChatRepository(this);
            //String fromUser = chatRepo.getOtherUser(toUser, parseInt(chatId));
            Chat c = chatRepo.findChatByUsers(toUser, fromUser);
            if (c == null) {
                c = chatRepo.insertChat(new Chat(toUser, fromUser));
            }
            msgRepo = new MessagesRepository(getApplicationContext(), c.getIdChat());
            msgRepo.addMsg(new Message(toUser, fromUser, msg, c.getIdChat()));
            // enter the right activity by chat:
            Intent intent = new Intent(this, ChatsActivity.class);
            intent.putExtra("username", toUser);*/



            Intent intent = new Intent(this, ChatMessagesActivity.class);
            /*intent.putExtra("notification", msg);
            intent.putExtra("username", toUser);
            intent.putExtra("fromUser", fromUser);*/
            intent.putExtra("notification", new infoForIntent(toUser, fromUser, msg));

            PendingIntent snoozePendingIntent = PendingIntent.getActivity(this, 0, intent, 0, null);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(snoozePendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManage = NotificationManagerCompat.from(this);
            notificationManage.notify(1, builder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String description = "New notification";
            CharSequence name = "Got Msg";
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


    }
}