package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.repo.BookTransactionRepo;
import com.example.bookrental.utils.MailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnDateExceededEmailService {
    private final JavaMailSender javaMailSender;
    private final BookTransactionRepo bookTransactionRepo;

    public String sendDueDateMail() {
        Date today = new Date();

        // Find transactions where return date has exceeded today's date
        List<BookTransaction> overdueTransactions = bookTransactionRepo.findByToDateBefore(today);

        for (BookTransaction transaction : overdueTransactions) {
            String memberEmail = transaction.getMember().getEmail(); // Assuming Member has an email field
            String emailSubject = "Return date exceeded";
            String emailBody = MailUtils.setTemplet(memberEmail,emailSubject);
            // Send email
            sendMail(memberEmail, emailSubject, emailBody);
        }

        return "Emails sent to members with exceeded return date";
    }

    public String sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        return "mail sent";
    }

}
