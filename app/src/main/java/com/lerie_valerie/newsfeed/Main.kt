package com.lerie_valerie.newsfeed

import android.os.Build
import androidx.annotation.RequiresApi
import java.net.URL

//import java.nio.file.Paths

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
//    runBlocking {
//        val a = RetrofitBuilder.buildApi(RetrofitBuilder.BASE_API_URL)
//        val res = a.getNewsFeed(
//            q = Constant.C_Query,
//            from = Constant.C_From,
//            sortBy = Constant.C_Sort,
//            apiKey = Constant.C_ApiKey,
//            page = 1,
//            pageSize = 20
//        )
//
//        println()
//    }

//    runBlocking {
//        val path = Paths.get("https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/nbf17bc7wqg5vqkbttbr.png")
//        println(path)
//        println(path.getFileName())
//    }

//    runBlocking {
//        URL.getPath()
//        val uri = Uri.parse("https://i.kinja-img.com/gawker-media/image/upload/nbf17bc7wqg5vqkbttbr.png")
//        val name = uri.getLastPathSegment();
//
//        println()
//    }

    val url = URL("https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/nbf17bc7wqg5vqkbttbr.png")
    println(url.file)
    println(url.path)
//    println(FilenameUtils.getName(url.getPath().get))
//    println(url.)
    val strUrl = "https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/nbf17bc7wqg5vqkbttbr.png"

    val fileName: String = strUrl.substring(strUrl.lastIndexOf('/') + 1, strUrl.length)

    val fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'))
    println(fileName)
    println(fileNameWithoutExtn)


}