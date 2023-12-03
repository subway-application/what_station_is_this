package com.example.subway.search

import android.content.Context
import com.example.subway.R
import java.io.BufferedReader
import java.io.InputStreamReader

// CsvParser 클래스를 정의
class CsvParser {
    companion object {
        fun parseStationData(context: Context): List<StationData> {
            val stationList = mutableListOf<StationData>()

            try {
                // R.raw.stationlocation은 res/raw 디렉토리에 위치한 stationlocation 파일을 가리키킴
                val inputStream = context.resources.openRawResource(R.raw.stationlocation)
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String?


                // 해당 파일에서 읽은 데이터를 StationData 객체로 변환하여 리스트에 추가
                while (reader.readLine().also { line = it } != null) {
                    val values = line?.split("\\s+".toRegex())
                    if (values?.size == 3) {
                        val name = values[0]
                        val latitude = values[1].toFloat()
                        val longitude = values[2].toFloat()
                        stationList.add(StationData(name, latitude, longitude))
                    }
                }
                inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return stationList
        }
    }
}