package com.heeyjinny.lottodrawingmachine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
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
        //6
        initBtnClear()

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

            //8
            //작성된 로또번호 리스트를 텍스트뷰에 띄우기
            //forEach{}문을 사용하면 몇 번째의 인덱스 값이 넘어오는 지 모름
            //forEachIndexed{} 사용하여 인덱스 값과 리스트의값(넘버값)을 가져와 사용
            list.forEachIndexed{ index, number ->
                //8-1
                //텍스트뷰 리스트에 저장된 현재 인덱스 값 저장
                val textView = numTextViewList[index]

                //8-1
                //리스트에 있는 값을 현재 인덱스의 텍스트뷰에 텍스트 저장후
                //보여지게 설정
                textView.text = number.toString()
                textView.isVisible = true

                //9
                //숫자에 맞는 백그라운드 설정
                setNumBackground(number, textView)
            }

            //Log.d("list", list.toString())
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
                Toast.makeText(this, "번호는 5개까지 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //5-1-3
            //넘버피커에서 선택한 숫자가 중복된 값이면 알림 설정
            //전역변수 numPickerSet사용하여 리스트에 중복된 값이 존재하면 추가되지 않고 알림 설정
            if (numPickerSet.contains(numPicker.value)){
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
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

            //9
            //숫자에 맞는 백그라운드 설정
            setNumBackground(numPicker.value, textView)

            //5-6
            //넘버피커set 리스트에 피커에서 선택한 값 추가...
            numPickerSet.add(numPicker.value)

        }
    }//initBtnAdd()

    //9
    //when문을 사용하여 텍스트뷰의 각 값의 범위에 따라 백그라운드 설정
    //btnAdd와 btnRun에도 필요하기 때문에 함수를 만들어 사용
    //파라미터로 숫자와 보여주는 뷰인 텍스트뷰를 받는 함수
    private fun setNumBackground(number: Int, textView: TextView){
        //9-1
        //번호에 맞게 공의 색상 변경되도록 설정
        //drawable에 저장되어있는 도형들은 앱에 저장되어 있기 때문에
        //ContextCompat을 사용해 가져옴
        when(number){
            in 1..10 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 11..20 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }//setNumBackground

    //6
    //초기화버튼 코드 작성
    private fun initBtnClear(){
        btnClear.setOnClickListener {
            //6-1
            //피커set리스트 값을 모두 삭제
            numPickerSet.clear()
            //6-2
            //텍스트뷰를 순차적으로 하나하나 꺼내어 .forEach
            //안 보이게 변경
            numTextViewList.forEach {
                it.isVisible = false
            }
            //5-1-1
            didRun = false
        }
    }//initBtnClear()

    //4
    //번호를 가지고 있는 리스트 형식의 랜덤번호 생성 함수 작성
    //fun getRandomNum(): List<Int>{}
    private fun getRandomNum(): List<Int>{

        //4-1
        //리스트 넘버를 뮤터블리스트로 만들고 mutableListOf<Int>()
        //1~45의 숫자를 차례대로 추가.add()한 리스트로 초기화 .apply{}
        val numList = mutableListOf<Int>().apply {
            for (i in 1..45){
                //7
                //조건식 추가
                //이미 선택되어 있는 번호는 랜덤으로 돌리지 않기 위해
                //만약 numPickSet의 값 중 이미 i의 값이 있을 경우
                //continue하여 넘버 리스트에 추가하지 않고 다시 반복문 실행
                if (numPickerSet.contains(i)){
                    continue
                }
                this.add(i)
            }
        }

        //4-2
        //리스트가 가지고 있는 값들 섞어주기 .shuffle()
        numList.shuffle()

        //4-3
        //섞어준 값중 인덱스 0(이상)부터 6(미만)까지, 6개의 숫자 저장하는 변수 생성
        //.subList(0, 6)
        //7-1
        //넘버피커에 저장되어 있는 값을 리스트로 반환하여 가져오고...
        //넘버피커에 선택되어있는 값 개수를 제외한 개수의 랜덤 값 가져와서
        //서로 합쳐 새로운 리스트 생성
        val newList = numPickerSet.toList() + numList.subList(0, 6 - numPickerSet.size)

        //4-4
        //6개의 랜덤된 리스트 오름차순으로 정렬 .sorted()
        //결과 값 리턴
        return newList.sorted()

    }//getRandomNum()

}//MainActivity