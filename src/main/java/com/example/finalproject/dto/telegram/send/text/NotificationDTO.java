package com.example.finalproject.dto.telegram.send.text;

//import com.leqolas.bot.model.TicketStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotificationDTO {


    public String fromWhere;
    public String toWhere;
    public String flightDate;
    public String flightTime;

//    public TicketStatus ticketStatus;




    @Override
    public String toString() {
        return fromWhere + "\n" +
                toWhere + "\n" +
                flightDate + "\n" +
                (flightTime.equals("0")? "Dəqiq saat qeyd olunmayıb" : flightTime);
    }
}
