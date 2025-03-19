package ru.netology.pusher

//import com.google.auth.oauth2.GoogleCredentials
//import com.google.firebase.FirebaseApp
//import com.google.firebase.FirebaseOptions
//import com.google.firebase.messaging.FirebaseMessaging
//import com.google.firebase.messaging.Message
//import java.io.FileInputStream
//
//
//fun main() {
//    val options = FirebaseOptions.builder()
//        .setCredentials(GoogleCredentials.fromStream(FileInputStream("fcm.json")))
//        .build()
//
//    FirebaseApp.initializeApp(options)
//
////    val message = Message.builder()
////        .putData("action", "LIKE")
////        .putData("content", """{
////          "userId": 1,
////          "userName": "Vasiliy",
////          "postId": 2,
////          "postAuthor": "Netology"
////        }""".trimIndent())
////        .setToken(token)
////        .build()
//
//    val message = Message.builder()
//        .putData("action", "NEW_POST")
//        .putData("content", """{
//          "userId": 1,
//          "userName": "Vasiliy",
//          "postId": 2,
//          "postContent": "Примените NotificationCompat.BigTextStyle для отображения текста в расширенной области содержимого уведомления"
//        }""".trimIndent())
//        .setToken(token)
//        .build()
//
//
//    FirebaseMessaging.getInstance().send(message)
//}


import kotlin.concurrent.thread

fun main() {
    val resourceA = Any()
    val resourceB = Any()

    val consumerA = Consumer("A")
    val consumerB = Consumer("B")

    val t1 = thread {
        consumerA.lockFirstAndTrySecond(resourceA, resourceB)
    }
    val t2 = thread {
        consumerB.lockFirstAndTrySecond(resourceB, resourceA)
    }

    t1.join()
    t2.join()

    println("main successfully finished")
}

class Consumer(private val name: String) {
    fun lockFirstAndTrySecond(first: Any, second: Any) {
        synchronized(first) {
            println("$name locked first, sleep and wait for second")
            Thread.sleep(1000)
            lockSecond(second)
        }
    }

    fun lockSecond(second: Any) {
        synchronized(second) {
            println("$name locked second")
        }
    }
}
