package com.example.calander2;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    ArrayList<Date> dayList;

    public CalendarAdapter(ArrayList<Date> dayList){
        this.dayList = dayList;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar, parent, false);
        return new CalendarViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position){
        //날짜 변수에 담기
        Date monthDate = dayList.get(position);

        //달력 초기화
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);

        //현재 년 월
        int currentDay = CalendarUtil.selectedDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = CalendarUtil.selectedDate.get(Calendar.MONTH)+1;
        int currentYear = CalendarUtil.selectedDate.get(Calendar.YEAR);
        //넘어온 데이터
        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        //비교해서 년, 월 다르면 연한색
        if(displayMonth == currentMonth && displayYear==currentYear){
            holder.parentView.setBackgroundColor(Color.WHITE);
            //날짜까지 맞으면 색상 표시
            if(displayDay == currentDay) {
                holder.itemView.setBackgroundColor(Color.parseColor("#C0C0C0"));
            }
        }else{
            holder.parentView.setBackgroundColor(Color.parseColor("#E0E0E0"));
        }

        //날짜 변수에 담기
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);

        holder.dayText.setText(String.valueOf(dayNo));


        //텍스트 색상 지정
        if((position+1)%7==0){  //토요일
            holder.dayText.setTextColor(Color.BLUE);
        }else if(position==0||(position+1)%7==0){   //일요일
            holder.dayText.setTextColor(Color.RED);
        }

        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //클릭 했을때 색 바뀌게 하고 싶은데 이렇게만 하면 흰색으로 다시 안돌아옴
                holder.itemView.setBackgroundColor(Color.parseColor("#C0C0C0"));
                //클릭시 이벤트 기능 구현
                //int iYear = day.getYear();//년
                //int iMonth = day.getMonthValue();//월
                //int iDay = day.getDayOfMonth();//일

//               String yearMonDay = iYear + "년" + iMonth + "월" + iDay + "일";
//                Toast.makeText(holder.itemView.getContext(), yearMonDay, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount(){
        return dayList.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder {
        //초기화
        TextView dayText;
        View parentView;
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText);
            parentView = itemView.findViewById(R.id.parentView);
        }
    }
}
