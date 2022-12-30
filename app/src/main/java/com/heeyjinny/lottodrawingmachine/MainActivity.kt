package com.heeyjinny.lottodrawingmachine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

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

    //5-2
    //레이아웃 텍스트뷰(로또번호)6개 연결
    //리스트를 사용하여 순차적으로 연결하기...
    private val numTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById<TextView>(R.id.tvNum1st),
            findViewById<TextView>(R.id.tvNum2nd),
            findViewById<TextView>(R.id.tvNum3rd),
            findViewById<TextView>(R.id.tvNum4th),
            findViewById<TextView>(R.id.tvNum5th),
            findViewById<TextView>(R.id.tvNum6th)
        )
    }

    //2
    //넘버피커 연결
    private  val numPicker: NumberPicker by lazy {
        findViewById(R.id.numPicker)
    }

    //5
    //예외처리를 위한 전역변수 생성
    private var didRun = false
    private var numPickerSet = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //2-1
        //넘버피커의 최소숫자와 최대숫자 설정
        numPicker.minValue = 1
        numPicker.maxValue = 45

        //3
        initBtnRun()
        //5
        initBtnAdd()

    }//onCreate

    //3
    //btnRun버튼을 클릭했을때 설정되는 함수 생성
    private fun initBtnRun(){
        //3-1
        //btnRun버튼 클릭 시 실행
        btnRun.setOnClickListener {
            //5-1-1
            didRun = true
            //3-2
            //번호를 가지고 있는 리스트 형식의 랜덤번호 생성 함수를 실행해
            //리스트에 랜덤번호 추가
            val list = getRandomNum()

            Log.d("list", list.toString())
        }
    }//initBtnRun()

    //5
    //btnAdd버튼을 클릭했을 때 설정되는 함수 생성
    private fun initBtnAdd(){
        btnAdd.setOnClickListener {
            //5-1
            //버튼을 클릭했을 때 예외 3가지 처리
            //5-1-1
            //이미 btnRun버튼을 클릭했을 때 초기화하라는 알림 설정
            //전역변수로 didRun생성하여 false값 저장하여 사용
            //didRun의 값이 true가 되어있다면 알림 설정
            if(didRun){
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                //다시 클릭리스너 메서드 실행하기
                return@setOnClickListener
            }
            //5-1-2
            //선택할 수 있는 번호는 최대 5개가 될 수 있다는 알림 설정
            //전역변수로 numPickerSet 생성하여 사용
            if (numPickerSet.size >= 5){
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //5-1-3
            //넘버피커에서 선택한 숫자가 중복된 값이면 알림 설정
            //전역변수 numPickerSet사용하여 리스트에 중복된 값이 존재하면 추가되지 않고 알림 설정
            if (numPickerSet.contains(numPicker.value)){
                Toast.makeText(this, "이미 선택한 번호입니다.\n다른 번호를 선택하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //5-2
            //레이아웃의 선택한 로또번호를 보여주는 텍스트뷰 연결
            //전역변수로 연결하여 쓰기...
            //5-3
            //추가 버튼 클릭 시 레이아웃 연결된 리스트에서 현재 피커set의 위치에 값추가
            //변수생성하여 텍스트뷰위젯의 위치 인덱스 저장
            val textView = numTextViewList[numPickerSet.size]
            //5-4
            //현재 위치를 가지고 있는 텍스트뷰 위젯 보여지게 설정
            textView.isVisible = true
            //5-5
            //텍스트뷰 위젯에 선택된 피커의 값을 문자로 변환해 텍스트로 반환
            textView.text = numPicker.value.toString()

            //5-6
            //넘버피커set 리스트에 피커에서 선택한 값 추가...
            numPickerSet.add(numPicker.value)

        }
    }//initBtnAdd()

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
        //6개의 랜덤된 리스트 오름차순으로 정렬 .sorted()
        //결과 값 리턴
        return newList.sorted()

    }//getRandomNum()

}//MainActivity