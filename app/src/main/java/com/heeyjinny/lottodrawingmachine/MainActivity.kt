package com.heeyjinny.lottodrawingmachine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker

class MainActivity : AppCompatActivity() {

    //1
    //레이아웃에 생성한 버튼3개 연결하기
    private val btnAdd: Button by lazy {
        findViewById(R.id.btnAdd)
    }
    private val btnClear: Button by lazy {
        findViewById(R.id.btnClear)
    }
    private val btnRun: Button by lazy {
        findViewById(R.id.btnRun)
    }

    //2
    //넘버피커 연결
    private  val numPicker: NumberPicker by lazy {
        findViewById(R.id.numPicker)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //2-1
        //넘버피커의 최소숫자와 최대숫자 설정
        numPicker.minValue = 1
        numPicker.maxValue = 45

        //3
        initBtnRun()

    }//onCreate

    //3
    //btnRun버튼을 클릭했을때 설정되는 함수 생성
    private fun initBtnRun(){
        //3-1
        //btnRun버튼 클릭 시 실행
        btnRun.setOnClickListener {
            //3-2
            //리스트에 랜덤번호 추가
            val list = getRandomNum()

            Log.d("list", list.toString())
        }
    }//initBtnRun()

    //4
    //번호를 가지고 있는 리스트 형식의 랜덤번호 생성 함수 작성
    //fun getRandomNum(): List<Int>{}
    private fun getRandomNum(): List<Int>{

        //4-1
        //리스트 넘버를 뮤터블리스트로 만들고 mutableListOf<Int>()
        //1~45의 숫자를 차례대로 추가.add()한 리스트로 초기화 .apply{}
        val numList = mutableListOf<Int>().apply {
            for (i in 1..45){
                this.add(i)
            }
        }

        //4-2
        //리스트가 가지고 있는 값들 섞어주기 .shuffle()
        numList.shuffle()

        //4-3
        //섞어준 값중 인덱스 0(이상)부터 6(미만)까지, 6개의 숫자 저장하는 변수 생성
        //.subList(0, 6)
        val newList = numList.subList(0, 6)

        //4-4
        //6개의 랜덤된 리스트 오름차순으로 정렬 후 .sorted()
        //결과 값 리턴
        return newList.sorted()

    }//getRandomNum()

}//MainActivity