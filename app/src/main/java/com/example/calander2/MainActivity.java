package com.example.calander2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.ZoneId;


public class MainActivity extends AppCompatActivity{

    TextView monthYearText;//년월 텍스트뷰
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //초기화
        monthYearText=findViewById(R.id.monthYearText);
        ImageButton prebtn = findViewById(R.id.pre_btn);
        ImageButton nextbtn = findViewById(R.id.next_btn);
        recyclerView = findViewById(R.id.recyclerview);

        //현재 날짜
        CalendarUtil.selectedDate = Calendar.getInstance();

        //화면 설정
        setMonthView();

        //이전달 버튼 이벤트
        prebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //현재 월-1 변수에 담기
                CalendarUtil.selectedDate.add(Calendar.MONTH, -1);
                setMonthView();
            }
        });
        //다음달 버튼 이벤트
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //현재 월+1 변수에 담기
                CalendarUtil.selectedDate.add(Calendar.MONTH,+1);
                setMonthView();
            }
        });
    }//oncreate

     //날짜 타입 설정 (n월 yyyy)
    private String monthYearFromDate(Calendar calendar){
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);

        String monthYear = month + "월" + year;
        return monthYear;
    }

    //화면 설정
    private void setMonthView(){
        //년월 텍스트뷰 셋팅
        monthYearText.setText(monthYearFromDate(CalendarUtil.selectedDate));
        //해당 월의 날짜 가져오기
        ArrayList<Date> dayList = daysInMonthArray();
        //어댑터 데이터 적용
        CalendarAdapter adapter = new CalendarAdapter(dayList);

        //레이아웃 설정(7열)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 7);
        //레이아웃 적용
        recyclerView.setLayoutManager(manager);
        //어댑터 적용
        recyclerView.setAdapter(adapter);
    }

    //날짜 생성
    private ArrayList<Date> daysInMonthArray(){
        ArrayList<Date> dayList = new ArrayList<>();

        //날짜 복사해서 변수 생성
        Calendar monthCalendar = (Calendar) CalendarUtil.selectedDate.clone();
        //1일로 세팅
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        //요일 가져와서 -1 =>일요일:1, 월요일:2
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        //날짜 셋팅(-5일 전)
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);
        //42전까지 반복
        while (dayList.size()<42){
            //리스트에 날짜 등록
            dayList.add(monthCalendar.getTime());
            //1일씩 늘린 날짜로 변경
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dayList;
    }

}
















