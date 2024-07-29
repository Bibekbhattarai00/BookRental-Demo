package com.example.bookrental.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RequiredArgsConstructor
@Component
public class MailUtils {
    private final JavaMailSender javaMailSender;
    public static String setTemplet(String to,String subject){
        String template ="Subject-:"+subject+"\n\n"
                +"Hello, "+to+"\n\n"
                + "This is a message just for you to you about the expiry date of ypur renting service, "+to+". ";

        return template;
    }

    public static String resetTemplet(String to,String subject,String token){
        String template ="Subject-:"+subject+"\n\n"
                +"Hello, "+to+"\n\n"
                + "your reset token is, "+token+". ";

        return template;
    }

    public String sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        return "mail sent";
    }


    public void sendHtmlEmail() throws MessagingException, IOException {
        MimeMessage message=javaMailSender.createMimeMessage();
        message.setRecipients(MimeMessage.RecipientType.TO,"bibek.bhattarai831@gmail.com");

        String htmlTemplete=readFile("D:/hobs/templete/temp.html");
        String htmlContent=htmlTemplete.replace("${htmlContent}","<p style=\"margin-left:auto;text-align:center;\">\n" +
                "    <a href=\"https://every.to/napkin-math\"><span style=\"color:rgb(34,34,34);\">Napkin Math</span></a>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;text-align:center;\">\n" +
                "    The AI Hardware <span style=\"color:rgb(34,34,34);\">D</span>ilemma\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;text-align:center;\">\n" +
                "    Why new devices are flopping—and how they might succeed\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;text-align:center;\">\n" +
                "    BY <a href=\"https://every.to/@ItsUrBoyEvan\">EVAN ARMSTRONG</a>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;text-align:center;\">\n" +
                "    MAY 2, 2024\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;text-align:center;\">\n" +
                "    <strong>&nbsp;</strong><span style=\"color:rgb(88,139,189);\"><strong>Listen</strong></span>\n" +
                "</p>\n" +
                "<figure class=\"image image_resized\" style=\"width:28.42%;\">\n" +
                "    <img style=\"aspect-ratio:622/345;\" src=\"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQYGBcUFhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSj/2wBDAQcHBwoIChMKChMoGhYaKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCj/wgARCAFZAm4DASIAAhEBAxEB/8QAGwABAAMBAQEBAAAAAAAAAAAAAAECAwQFBgf/xAAZAQEBAQEBAQAAAAAAAAAAAAAAAQIDBAX/2gAMAwEAAhADEAAAAfyoAAAAAAAAAADSmupEr6zsjLpjowy0zfRcDpzhtvz6Y7dvFLz8nTlz6Z6VmK22laaJNO3g2mtMr0s9qnznoS6THPZ3ef38q8PJ01uOcQAAAAAAAAAAAAAAAAAAAALVo0r151u0N8da7xyV7IxrGt7S66cmmssu/TG7Y+t5vPpw5b5S9Hb5P0x5vnep0anB1c+01hw/S/NJplrkdt9unOuf0+bpl+bx97w+mOSuuTKCAAAAAAAAAAAAAAAAAAAJ1z13lMNToVz3iNM7Z1vbHTWMb2yVvnrFZphz6d/JTbOqadKaz68cJY9HLGz1/G9vLO9fG7PXPnO2/ann6daax7eGp28U3ufmub3PG1jMIAAAAAAAAAAAAAAAAAAJq21XTESqbWnLWb9KusbuHTUrr3cKVnLXn0rTvwxvGdMM72Vob6Z+lnXnTj77Vfo+Zz349vo/mY6/L6dLL4+V3LtbHOzfg5NNY53Vazw1qsgAAAAAAAAAAAAAAAAANctdQjXUpeL6zpXK9mvN04k6Vkvz5s3r9Dg9DeHn0jnvPbGM9Onmv6RX044c67P0f4f7fn16PivsfJl8P1/j/VPN5Ojhs6fL9Dh1j068vUvm3npMOhTWfM5dM7gIAAAAAAAAAAAAAAAAAm9NdS0nTCtZl0mc7NYpeqRemWdoZ00zsm1ufQtfLrmr9Xn9c16unm+xnW3reZ6uOnbTqzx0+a833/j+vLs8f6Lw05tYx1j6DzJjOp387Ca749DyNZ8kXmAAAAAAAAAAAAAAAAABOuWmovDeZKmuc73OcXyWsSzqdMr2TTTNF2mNdXqcGbXdx8fZNV6+715fqMPpvN49vnfI8rs09XHv4JfnvO741z4dK23jh16+ON8behneHD63h6xykXAAAAAAAAAAAAAAAAAAE65dm80ibbzjFrS60rSxLtKcvb59llJxu8xCbW5bS9l+b6Jr3/oeO3l9mXucvFHv+Dw/Mbn12HiXZ+gr5vdm/OfQfD+rvHpefhhNc3Kx3y6/e8Ttz0eZ7Xlax4iY1yAAAAAAAAAAAAAAAAAA06Obo64iZi5rElpeNDXp5cN82UufWx03PPHsbbz4D6r5nNx9Dg35df0Xr8L2+Hs8fwe3ydc+PH0PpenL47s9OtzjzV5M73zz0RNcl73Vhz6Y9PnelXs+B9j8UnkJjfEAAAAAAAAAAAAAAAAACdsN9Z0ov0zS0Vib0rVZjWWcuuu8Zd3F1XP0EeX09/LHNpyZ3ze/4H2Pj9/3fTlrx9fzNfss+bxtPS4bOinDxani/HfdctnxU/RfP9OfPFdLj2fKp0c+nN7eVj6b4XXns4qzGuYAAAAAAAAAAAAAAAAAE3pbU2tjO83iyyk2zlpvjvZ09XF6Hp8uenb0b83LPZffDx+L1fL4+zk/TPzn9h8P0vqufr5uPTj34O7n1tw98s+Xh28rfl9PMa8r4L6X5Lty5+vn33y7NrU59OPn9HLeePHv8zWMhIAAAAAAAAAAAAAAAAAAtWam1Js2rDWdq5TSyI6+jC/p83f6Phxvh6t8tdcc/L7PPx6O39p/C/0zwfU+/wCXz+bnvo14+zl06ubbztZ08vTz5vl8zLz7fP8AJ9Hze3mdnn6Wep1+JGN+94e/FrPX5fVx3AAAAAAAAAAAAAAAAAAAACUVa+etyi9dStrUOqcZ6c47uD0bn1McaenxZ+Z3ef5vbn08jz+j2cvM1t7fsfJ+ox0++8318OO/C+a+v+Vz3+am+nTn5fk+p5fTjxdXLpefXPN0tY5b8qVgQAAAAAAAAAAAAAAAKAAAAAm9Cb0tG86898jYmyerl13jrZR15Oe9OfXG1/sOPb4/9F0+w59ejbg0577acLN6vkve+YnTzu7G9fM/PfQ/N9vNnrW1xMY3q2N6AQAAAAAAAAAAAAAAAFAAAAAJgaTSdZmtoL783VvGd1bNa1xNYwvjfZ9n8J347frfd+NdWOv6Tv8Ania+u5vkeBPufIrgv0PB6XynNweH18PfzaXvVM4VqBAAAAAAAAAAAAAAAAAUAAAAABM1EzFrK75RZ2Uh05zTQc97Vxv0ft/za+O37LP4vOO37J5v5r9RNfQz382Lh5Gvmax7fw/qfO7555S1yaU1K59PMQIAAAAAAAAAAAAAAAACgAAAAgBMKt2Za9eXPl08+dNcpl3mjeNLV67nm6ff+159vzSP2rfj1/EPq/0bjzv5vi9jxmvJ8vb5vrznjtS8psE1tBfOwzEAAAAAAAAAAAAAAAABQAAAAQAtbTWd4tTvyzx6MufTMY01yWdW3Hrvn9B9b+d+9nr+m9fw3b5vV9ZxeF82e78r53k9eLBFxMyEBMXoXtSCiYgAAAAAAAAAAAAAAAAKACAAAGtOvWaZQrqopvGlUGVbRjaJklXVKXmp1zxy1tmpKxi0SmwpahFosXztBFZE0tBAgAAAAAAAAAAAAAAAAAAWqs62sysk0rbC5mYLpFbWSgVTWWE1iYqXa3PJvGCXSkItMa1mmpKLExNTTO+YiQSKLVgAAAAAAAAAAAAAAAASRN5sXo0tFoRMTZbDfIVmZaXrEus56ayymJazW2bVJYEJhVoSXqgRMkXrYREBMlZiRNdjNaoz6KmKYgAAAAAAAAAAAQSQSSJhUouk3i2oqrZM1tCmmYtS6zFlmUzXOpiYIETFoKxMrCblY0ySUFsACLRIiJFQsgWVF75SWhBWutjAQAQJAAAB/8QALBAAAgICAQMDBAMAAgMAAAAAAQIAAwQREhAhMRMiUAUgMkEUIzBAQgYVM//aAAgBAQABBQL/AI4EPRI7cozdhK7NCy5TCdzjFrJhQCMsM1P+s4wH2DxvR0OWT3iEiY1oec+12+SZPE5KkgON2Lv44Tx0Xz+h+U5dpxE5IAjwFTLGAhZnnHU3GnD2a7sugp0OMTct3w1s89NWxl3/ANKgOeIeN2VUarvEPf41R1WDtUVg6eINmBQJsQ7MVeTelwS1OKKNLF/CqrdeUJxIlQ9vZQylYW0949wcwL6lZUsn515g/kVOhEXv8YJ+j3gE1LCIDtVX28NTWyG0YBszH1Wyalx9R7PK+GjN6VZPdqgYh1AA75KAoq7ss3Kh3xdVs6ndVYsGNZ6VGRj9vwZh8WsMESM2uglUtcE73AsPlfaN6C2AF8jkvMwAxFLMU4m6zlQp702+7NqAZSeVjerQRqOpMxE4ZV1fsJZq8Jj/ACWIWUniMuiEfFDqINma2Z4CgmCpjCOMJ7KOMMsPYjQAle1DIFKnS2nbaLNXWPVINZxnDPl4xSzHf0rbcdWWv8tgtknhZrjT6ZLWvyn4LbcNMquh+JHQdz+/2xnmenqBlrlmSWXRY61DqHuTG7qRxC+1GbYT8lGyqe97IxJsWgCL/amTToYt/GWpuy0sW5+rjIXEr/8ArYqlLLVItT+wHi+QPifHTwomvae50qqzDQianMKrvyi+V3yK6PHRaKNgwsYgLNYyBVXk+LhmyV0WVLjL6L21F6rKYHauXNzrquauJcDZTsX5oauLA/EFByCcWYaPwywzyT5UdyYPLd4Z/wBFg7AnZXvEXjNhhYxWb3BvjvingYp2+UE4/T6hY1VYraxBYmWHrTByHFmTVW8uXcSzUtXlLD70bZyi1zDxrdS+6b5pad/EjtAJ4g3PEU7muR7QtCST4lVvE2Wc0IO/Ted9zh3px/UbEweV93dvpiccnnuHlx+oVeotZbHuscrHcGP4V9rqVAiA/wBnCOvtw+2TkL6VjHfxCjq0HeeSIDCDCOvaCcgI9pYIeJEUisV3BZTYfWKfyr6u91TajszivuPq1H9WJdzGVikQEhh3g7tmIiw+1R75WyucffPNbnT8OvQeT5ngfqN45dvs3O87a7FvTIUuYiaWw8FwWdkoTUr1A/ZzuZnK2cGpsvyS1b6WDw++QyNp2KmI3E2FbhkKwT4cdB2BniMegmoZ4g7zjCDNTcXcx6zyUbAvrEfI5s78pXYztViClGRUgvTeTlMXxQ1deaimIhd7QN1EceK6Y7IYxz7tSv3Le5J+IPbp4m4o5QqBNgdT5nIwGGKpM7wdgxfYR7CMS8j/ANf/ABqf/H8YGwHYtrOvqPtysekUOeRn1Pa1Wt3sxzPT4QIXsvp4rV2LIWvFQbGrHuv7v8Os1PyJ8jyPDN2giqNP5E313NznoV1tY2HhBVr4IuRV/KlFa1VtVyGcclBj4KBbcYBlPEZ688SvHXIlGG/pPjFJxUHKyPUZP6wv5Vru3l6b5lfF/h1Go08CDy3aeYJWmpYeo3OM11r7zD1zqbUHlNCX5QqN/wBTKouaedeb39Z2YX+5Mkem7tTdV9SsC25lpnq+orgqFg0DSDXZkJxGSdp8MsHeGN3PVYi+6zy5311O8OxNdPExruLY9vJWfjL8oiW5BMZ2MJiM+q8ga/kGNa+2PKNAfavckCyLVyCni6HUTd+NmLxHw6TxP2Z+hP8Atz4wt211pr5t/H9M1Yn8izLxUQa7sJSJhI0spZly6WVzWxldPOV/SxZjDAY01fTmua/Fat+46frzB2aun1lyX4le7heQwq/4+JmsWs+Hr7xj36rGMMHQxW3KWCmvLAH81iHs5jvHGz9Lo9S2qkKrBVmRiva2Ng+5sWoCp6FFgwro1mJTX9TyEMsXUQRpuKe9D2IGrJIHCfTMfdmY/OZHZvhx4mu0aDtP1FmtwpOHdVgSa0DHME+hVu1ltFmqK2Y8Nn0/dak9MS3HBllA42U7L4gItxOAuXRgX2174+s6n+mPl6qx7m5ZHdvhx4WCftun/WLEgWcIiwCERhG6f+Or/X+m7CrzPIZCpMeuBNu1Y3l2cZeffE90rCQJ6syayjN+YX3OfiVPRuh6GJ4ErMScZx761G1LJQvO36TT6day3wzhWB2Fh8MsbkIz6Z79Ll3blx7gdgCoqx/VU1XY5NwyJZXPwD/l8SOoMI6JBFMrPdX1GyOLjkwbxb4+nru/DH9QEsnHlb4f9M85GG3ve2y1pMvbZsimXkcKb/TlWcQ2TSutkTJs2D8Yem4YvYgz9q+p6jWNXXwjtGMsb3U3endgfUl4rmrqzOSY1waM425l35M/GWXKQ50WfZt82eVh/sbjoAd63nNgbCCPix0/XT9iGNuUjQVo5jGWHvuJaVgzLI+U5leZcjYn1NrHMuXZtSZB99jkQIODd5YOlcJUxt6Mbu3xon7byB2/cPhO5r87hMeP9ldbWNR9EyLJ9N+gtTkdow3Lk9uYGD+FtrJpP429CdwQDu7BifjtweRGm+8PhYPO+hhmoqFj6T7+h4ArCAJNwmblo9uUnvVeV96f1ZI7WQCdhOegZv5Hy2+8HUTe5uGGAbn03GlGJ/ZVXxXmQFfYNkNsss5JmO28MbluiMrw5izXQQ/IiDyYvUTcJm+lXENg/UceqUXY9osy6KFH1DFuPqIq2ZS8myhtL+czLSxp7YmQ39dz7VvIE0Oh+SHVYJqCGE9OJMWvcGG5gxMhImBlWvZ9HsUV/TMwSynMWK1u8SmwTKr4NvjVl3blh6V60T8wp6DoR0DSixQcHJon8vG4/wArGlmRXPWrY+h6hbEBFYIXKPK/NbjVc/JnO+v6PyoEaCKYeuuvKbacmgdp9MTlA2hbaTL3ICe6zOt2zt1PeVxtcflG7Ruo+zjPRcxMDJef+qzI30vMEq+m5JfEwrKgV9rHjLu6tYK5a/f7VMI0fkqR3MbqOoi+cY+7ECgDxO0vXUYbFpVWzr5Y+/8ABvkgNxfxjQ/YIIp70kcsazilVp0H3A0yLV5ZORo5WXHfZ/wP4/IKN9P10P2gwGJZo4+UBEy4mYJbnqi352xkZReE76r936+QRdwnU30PU/broGi3ET1jGsYzvCegmoew6j5ICeF6/v7x01NTXRj9gMb7BP31Pxepqfpz9x+8NNzc5Qt9v7g6CDyfPxYE0Oogh8/YPtP+Ig+8/GAf5joIYeh/4Pn/AAK/B7m/uP3Dofs1/wAPfQQjoIR8IPuMHTU1/rr/AIG+g6nzr4D9f4CCGHp+v8T4+399B/p+v0f9/wD/xAAkEQACAgEEAgMBAQEAAAAAAAAAAQIREAMhMUASQRMgUVAwYf/aAAgBAwEBPwH/ADeK9lHxq7wvveH2LGxXyeWKELCy1isPrPFfuHV7nPB/wWbxXsoX0a6/Jd8HhHlik5YQspWIaoos9D6rzQsKJLkS+kcJjHiuq/8AJYih4ebH1HnZbZdizyJWJnsY/wAxWJdSTyl7JSohb3+kFbIpY8q5PJFikh0e8S6kss8W2cEpVuLVTI6nkJiZKxoUjyearcRPjqM4xQxSJ0NxXBF2aS3KXNnlZSZ8SHpjjWLs4JO+rWZcGpZLW9HyGk7NJCGhOi8arEUPsUPge5LQ8mS8VtE0Y7mn+Ci8ISGyQjyZJ9d5o1eKFDc0lmEpJiYuBksX2orElZ4kVRHchGt8UR2xPuywkeJBP0jxl+FP2jyovYbpDd917lYshqeItdHzXssNmpLuye4h4Zpxcj4yKSw5V3W8LLRpuhUx0hz/ADutlfxl96/gL6L+A8rF9r//xAAkEQACAgEEAgIDAQAAAAAAAAAAAQIRECAhMUADEjBBE1BRYf/aAAgBAgEBPwH41hvai7PyOqOR66wn2KEh/wAPXFj1Xi+xHF7bYV8FVzh5rH+HA+ytscFVyKT4GkhjQ1oYnZeV1UciRY98OQnsN6JCY4/wXYWX8LELRQuos7vfKodaG8UWLQupFYob+iMbJ1xobJWzcijfDi/oV5XUhlHsksRV7D8bRKFYaFQj1Kzf1iPUjyc4sQ4kLN2S2JjLPY9z3E7xRyRVdRF44I8kRRKJnkZLgRWfGhli7Fi5Ee9Cv7PI9iasehIWKQuuh4s8fNlnlZZY6ehdyTxFljHsTd5ofJHt3iOG6PYe5RR6FbiVvvLZ5oas9T1osSIxruwW1jFmb9T8g5sQl3UsPCLJqxwYoNijXdS+C+7WHqsv9A9D/QLTXa//xAA7EAABAwIDBgMFBQkAAwAAAAABAAIRITEQEkEDICJQUWEycYETUpGhsSMwQmLRBDNAYHKCksHhkPDx/9oACAEBAAY/Av4qqooFBh23LSrgLhnzVTux0UoZdE2NU91kZ1WTaeJtiiDVunZZ5VadwhtWVnVGQB6KRXmY7blVaSqUR/WFTL8VlXfGe8KdMAgQiG9JlBpvhTTAtt0RaW1Xsn+F9E4dDzMlSjuVUqcB1KyzJNyp+CKrZP8A6ZTRoGxh80OlWrMbSuqOuuAPSizAcbbrOKPFPVbPbDpBhVv9eZBosAgnKqqqXwi5WVt+qnXRZ9pMFDKKWhRNk4qOi0iNdU3yH0TwNagp2aie3/FDN5yhKFcBms6gXtBdSLOoUc1Wgw6dQiRxMNiqqnL4VUF2UC2NF3RUmqgBUvhDUQUwe7TDhuKhN2jfA5Uumke6ge8r0TM44Tf/AGntsdmVnbqshNH19U50CDcdUcpNFNAb0sfLl1VTdtjXAdMKI5ddVT3UeyzIDqnB1B9F6o7DamWH6rshNWFEN/tUOmv1TARpP6pzhZ4mU5w8/JAsvNPNMLaVlNe211HiaqFd+W0whoUmpXFB+agCBvSqorzKdm8K6IXGvkoipTQhnMTWVkfxRZyqvZ7f4qL/AO+64qEfiVYlQ6Yd9U9sQ7xJgFpU/EIQRhmbbl4GuF4WuFxhaq949kEHGx1wgKELKcwrVUQGqh4OUrKTTRSzicAiQDT5f8Qa62iLmCs1Cp4VEuyn5JhblzZor1lGfG1yrHRRpN1BiJujsnjlld6yzOxBMEeao4LRS4k+q/Ku6CFLLNmqhKkDRQbJrXVGiLTLm6HsvaMOUqNeiylZmX1RI6rKTUj/AOJzyZOM9AEJ8YEL808qkqcO6GHZeeNR6r9581+Ert54EaqE0EXoi13RcNlsut1wz3UiIIvKiQXoB1ipBOX6KqkLuMMx1+ayxwlVug73jKaDUFO7CinlE7vlgcK7tGNVBTtgYhC/mqBe0JKcNkBBNVtiy8/VeAjuhGaCpNY69VmH4UNm74qWLKcGjrRAWUmKH5LRP2YsRwol1Fsndo5XP3nVVb8ChlapPyVDKlDKuAZGNH1T3ixcgCL4QyidsxB2jb9EKpuzZcqLu6oqEGbTSzll7yV07FSKELOOHqgCOWxh3KjcvuUopzZe8I/bSfJHO557YBvxTdhsbE/FNAbSETXoFLXcNp7pvsPFr2XE2HG5Oq44EVTnWaoYadUT3CzuI/VVoEBqnCLYdIURB5VXdoPju6fBafDc8cE+i8RVKqmyXtf2mv5U79o2l9FdGVl2UB510VxLqytPKFs4H4pKbs7MheibBlNa31lcIIb3X5kG66ra+/s+L0TtQ4TyqTu0367oCrdQIQBNEGtsqEhBjNswk0o2qD9v49ZUMMBZYqpF2mSuF0O+Sh4t0TgRZHMfZkDooE5QvaA8QNlm9U4s8LmFbMg8Iup5UMDueaJ+H3FUMdEFwrNPGdVO02lfJVkBcVPPVOD+lkfZOLV48jv6ZleIHusriSVM0+qqpFRZB12fiW1BsF6fpy44Q26puUUHdEKqjNuFwzZReF0Qh0+llIOOVUqVXoj7Mf3FQLFQbWRaRxt4J8v/AEpo1iOVnc7732ktGhC+yzOF3OKvuBTqqqAuyp44WVu39m4mHMcg3Z1DBLnKF23JWaobqiwGgpCB0RLDVOLxdF3Xl0b1RKgF6gTC8IKMQMB0QgBE9FNh0UuQORshDNs8p/Kvtch/rCgOAb0YFl/Z2R3RGJVUfZGnRdfRVQL6n8LVla4E/VETPLp367tCAFIPhqFn2v4qx0R3ArKlJVkO+JRLUfZuIHVB75TslJoEOI5tFMV51XHtuVwuqIjXqjhIUtIafdcvshX3XKt0OwUjqiOaWxOAQxG5ZUXeFHzVca2X/ECOGDSKgqNuAHe8E6L9Oqz+nL6bvngQES91+mICEYhRgIRVUSq2G4wSquQFIWbZscPnzaFlBVa6ziApQqrq6Jm6CCb0lGRIUxCNVl03b/NfovZudwmnkq1PdDmIxj7i6upDytk03zBWQVFAUKSYTosNyijCeXDGdyv3MNElVhqbtdvtJy1AGNVwqoqjAU7oJoEYtz2iiF7R/iUDeIhBtJuoxuqKvPhF1/zGcaFSnbR1yUZU/wAhVQBCljxCnaPARA2wC4HD0VaJ0WQATRKENUi567ni+H8gcMrhX2hI+alu0/yUt2rW+qOaHQqiESVW5TBtPdUbluf1Q0UOiylt0SXV7qGulCVUH9VkJENom5YhCtd0fyDcrxFVJWaVpKltkTxBTdQFTdnXn1AuHZOX7or9y4+S4tk4Lip3XpdUNL0VZhSD5LvvV5pP3VgqncoLo+KEYFtVSi7/AHA59NxuDM4NUDUVHVETzyn3lVR0uQh0o2JsjJqUANOeQP4C+9T/AMDg/hz9/wD/xAAqEAEAAgIBAwIGAwEBAQAAAAABABEhMUFRYXEQgVCRobHB8NHh8SAwQP/aAAgBAQABPyH/AOf5UTfeFBklNxwDaweG4895lnjpCoFBLmhiO8wTHAHFN7AlX3GJcxAZDGXM/MNtauFSnU95aicFFdpSw2qnpLUVgCD2pDrbDABo2PaPlNHmOkyzZm3L9/mXC8TRzHAtw8Gn/OkBjiQG/wB+kV7cRtZD8OaEW4bw4zORzHUl9YZCHyPMBy/aG49yLeB3BCacbWqW+XxaBFhDpwR0RhxicMY1L6tYI+DPxL7I4/E82b8Ma8Srh1H+D7yyzs29un0v5xRm3I+ZYnkgq5Fzz7TFqoMrv2mxIVXR6kDc9J10xEFND2ZshqYad8Pw3BcYdphb0+8ON5ofvMydQ2LpKpl0gXzBeqYXTpLu3mYQIZy+mcHWFU2HAdpnJm79kLdeIhGXYcsK8auvzipYhgRxKAo4aa6xdKw0rjJBQqhadCxiGWmfKuJkV2t4uU89pNbs/uYGc7lItKLsKVMNPowbftB1e8ryKml9PMDTuCajAnO456e8fBPb4SbZoHXcyTNTtgt1zidsU92VB7/WBZ7TAIniJYeI7RUc5Yg+wjQW9SuO8omW9IQNLk/ErVGgFEd72jUwBpKO8aJ7kqaEwe4/1CyvJjhjDnEuUXKgWTkeH9CXA5WEtFVh5z/cqmjjBNQuYvt0lulypYZCrr9xEnCHIuPeKgq+7rWvO4YzgwbP1+czHxvZznv95TSOeIeVY+FE5sS792abxDprxiUab1BwwvkiyF1bfWcBW5bKbYvN1me8pw25uAmU18twuw8zieP94KDsQVdtXcZLKv19plItmrlCz6h9JllOx+5CZK4eeJeBaS9uP6mYFdLjmEl0mBXU/wB+sLWNLfKEqeFhkE0b085iKKmnNHPzxCEbAr2Necw6m11eOidn94l2Iw+w+XaPn6kfhQ1UXMpv5Iv4EtkuaKKuVgeZaJ/U16eqQr9Yj5Tah6xlipGn1lK8so5Zx3iCB1DgjPyL3niQv9+c4IZZQaoseY3ZDmK2FujN9jZdfr6zrLC01XWB7/YJ3fK89P3/AEWKFKLND/PpLELPJdtPpMSU/Nm/7GDFFJTN2w59rlBtb024+Y/ONY4IG/b6fWV1As395bYU56fLhj1ZdC9ej/M5jp8Jzmq6weCVcBhYmi2D1DlmG0XvGONov8J28qGuxA1AuNN1uJEQKlnGYENuPFTXhXExGM15ZSqtLDRJruolV9ER2BVDk9IFhysvQ6QOWgw5WcFcf1A4BWcSygTRd26P6hKlrXUH7n2iIqdUpo8DvDN+EM+8BUUdN7/f3iENCgVNDGT93MzrVPfZ7TSEsr0tXULvJnvYv5ZlFqvGHdFnz/EZCsPSC0ZbddfhBuFDvLx3Z8xnRGhxBhcHaUYZNr0JxueL35ljb8NxNyZ948cXoi7drme43zGqc1Q7TwcOtzMrReDcs6P+iaTiArIta7/tQBbeL7VGyxzUuFL6Xf1JVUc6vmW6Nyyj6zoBC7jXizfZ2GLthFcnNnvLcNBYZXfHVff6r1kOX8MK5uBz+/11ia/QmBJbQ2uT97TDJm8AA/iKU1QxrWSMvKlf6gImyUHGZm0dRg7+NTkNox1rEsDp8HHLqLNcu4GCYwSHSb+sOA7eInDF9Yqc71A4cxZqyhgOYuY6HEXzTx3qJVcjRmFd1vCH5nQlYM/aCvjFQhUxTLXMrTi242jrmWlAZXxH5nQBUMKVdZ9555Xbt8obCq0H5hTW5fRfyRI9NNguA0caI1vcdPDu31iGjf1/est+zVh/uUG0KhNgMU1eMPGJQEM1W+v5lRDl1bKItWa90/iEJ1JOup2O7c4/SWW9Fff4ONBD6w1vniWQGlvlGBTcwBXsmw20Mwq95a3r9e0AvrksvDBYAKljMKxozCeEN3UM1h3IIgRi9aqFlg8JspkMwKA1X1mBVli+uP5jUDhi+lcSmaP2lfeFFtUlEMdvrLmWX9vpUHdK6DnodP7lxYlzZjd/l+5jtnXPTw/iB1v3pLZ1p/fnF0NXd48TAK7wdmSzy/uYovka33ltCziVr/ZU1Q1+9vvK+OLl1gOmH4MQ32nsCOXvGhqYNGp8iNjTjCVzZAwcXzLhtOMTM2zzUrPoRR3N75C4DKB0VKFos4esu7S89IxWlcaZlTfRW9mf9lRfUa8kYibjX7iZaoYX0V/mNS3DijS/qzDAKSqWJVjXh3JaTboYOIWK1RbOJcHXY1NI80wOFhTC5DL5WBBjQD0O/wBMy2YIA9aSgooDb7xmvpqcSzIDB2x/RBG2X/ByHrNHeOup9ByxKq3VwtArM4CLYrW4UHL1m+YztPaCrFRuq/NBa+dRHWHlhmgOuQRtCnnrC2Ofr+sFKzWa46TArBK7eXsWxBWsUN5Kx+JUZoZHNzKC33uIhLuG+uK/EQNxCvDOPpFw2rk1O4CsGFVtUjurOd9es5LJ94iRXBPt9oUWJX+CZ2GeaCIecXfJBiDxThnM0vqfufg/E1t8RekBY7dTNIHKYzhBttmhylUTIQkxaLWLBdQmcWapWNHMoHWWYVecyq0HkfsRUL4c1e+Ze7Xk1f0gbA6BeDoQjZWbTolveEpvunWpC3uXjT5lblURWQ8ukC6vAYtf9QYFsG7S8V0I3B/gKL/f3HWdnMfL/JdHAB5/yU6hoeU4UtGOO86pLWM3D1Qv6m49THd/ubZls9uksOjxFv4PeAlFicX7Icudw2XzEVHusBL19WcivV8Jno0blPPlm/nmZNSnGXmF6XjNmgIVeCuk64Dp+Eubrd1i4qy72Dad7P8AYXaEari+IKMSTGNQHzxHUyAUdfxmC7bcFPj7frDSH8z+IUqTUO0GnVjB06vvBKOm3sYcy9wXeP3vCR7rhmFgRz903QF4MTQlbd2HfhdcD8yurMYc41FYxy/Bu8PMrfL0h7E0dIbpGGGAnZPvDN4mS9eWOsiHB1lLvpY8oVDiVLXhxBUD8p1UQnTSYpt4gpajk4hABABYGkiiAofMbgoizhVuLHMUHSOCtt2hoC0zczIdav0RDqaZYe59ZcKHBP39+q0pBstfiIVQ6r8ywC+SSxipMLmGKG80vEtkw8rMj9fozFCh6aj8G3AN8/aaPWP303mYpFaoGXaa0bYVBy7Q7DwJd+lmpaeWZ7y0iF6K7iWXmMr+SWky+vcWrHepzwKqr/mGTcOmo+4pgbex7ykJTTsx8ovnXZGFXNtPmbJRAm8Au/7jWypuqnL06vMMpu0a6zJIGRF3hay1/PX2mqCave6/B7Tb5st+1vtHfwahl4mS+OJn6Tt0zBxnmOW2HFpVpnR5lC0OkFdEITslLCV6EsoZMIxpsifOWwgQKDMUdR9I/olvbfSYwcRTm4EnkNPeGzy6uZQCmYOUDAzxL1tCwiCRzyXL0eJE+F2fJ7Rj3ddH1mdMnEdYhWvJ+kwVAdg5fKLt0s+R8HNx1v7yrVvpHMr54b1KXbAvshs+SUINvqpNSuOZoC5YphMjGo6B7x1HtFN7jrglAVVwyG1xKQHMVByx02IcX3jjmsF8xBmAyXWSw/czgnQ46kp01MWYW43vNG+IK8sblbcG5knRjtfvMm0pZdxnq8a3KhhOucH3/hgI0b8yvg22C1OpdbvFyVzA44jizpM2UUMM2glTFlCmCqz9oKC6lDF0sp2j0rHbuYUtPY9LqeG2bhdSuukC0EanDS3ACGXUPnNY35ioRYHC68Rzh6pazUC8fVIihDjDL3jXNwaurjzMDwgtOemXgNMktrtN8baLXLHV1/iZvfNHv2hWI1p4fU7Dj5xuELl/B8cNxYbP24OQm0dGobco79BC5g4fEZgg8Ys2zETctpaxJEjzZydpZ7UAMQDlHErRlGcZ3Oz/ABFFxuve5cnzLq4TA4L3Ft5VimJWM/hNL9Z1uYwtQuDIrDB3lbvN8JpcrlajVtTWb83+8Tg8S16f3EVronPSPwY6zbMzvFsYc+WCGPaPT1jhgxNql5iLdGovoqkr3DiOJ5Z6wMSWRrXSJ55HMcOom6XAyQ3VMSww1zAqyOYI4P5gCnAw+Z1mrju+Ji0Jm3UVdEZ1P5/dzIMXe326/eChQ6CsEe/MWmMMu0uyYHHwg1Uol384NpLFhRWduH0uN5WyikBxA8GNay6MLimlw4iKjpJDN5xB2+isZbLShy7TZ3bITUAOqLnTdzIqXNekvNx4sjab75fpAhpY1YmNqKAxff8AfaLaZXPOnUgX8H5r/qYI+EcdyXzMIOu8XVzHnMLsjnzOmCZqmlzpAFrUqIrwSxG+GhHWHpktgiUwk6YmxxqKijfM6rjFBdxIXBzUudXGq5lDj25hpk8kdLbm2JqJzi3ABirSF+03GG12b6Q1At2aH5lEb5agCVwUdKjt+EGPQhk8E3JbCniUSyc2aIIQSeGztEHCvZzM02NFuZpbZCeBMD6yhpj7VqUbr0iXgIjVuJQNO5ZCkyMehLKx9ZczZeORl7q8Y6QRww4i6d6lSFWYdPgCFax4ozhb74q2qcnlx8n8zpEXqYkG6pPz8MVM2ET5oFbly4OqjE23HhTO7JYPJ+1LAlV5nQl8WlZSwB095XH0wLc+ZuMAy1EJoDKjlXLzKcPLbM4uunEuxh3ZqV8QGVLOZU2/SOG73l3MuoUQ6Eai5zL+G6+U6CLQ0Sm72jpBiOWZf5KbjmNHEuix3hzf/ABycEzy8kJaXRczSofYShRUkZzbEAdi4OUc31zECBtixOYrwV2JrnENSxSo9DUXw03DKbekHLFmpleGpxSpVx8IrmLHmG/RUjNA71VQSOf0nTK5x23m5VGrrE8iE5DRvMDIHggDEW00ToFnSJlkAM+whiAJZ2w5jg9/iBr2hgO8bOczWJknaOszBHlF6LEAVR6k1Aq1eahQLxVd40TvWOIIWt5fa5op9t3NWisty8qb0mpUadC9zNlYB7/1KK/rK3tyy63rKJllTm/ESLFR6+H+YNVN17TD0OEqi53muJh6DG4xLIokd5kmuBnzCbcuwHtNuTFaVFJSTIRCsOFBcew014O8ycdiEcmRzrrBvmCLo4I7ha6HWV5yjkuLPxF17x58zfknMXEzMxGJv64VxDlRt1+Urcl9Jy0d1dStdcVjGSdBmd9HsXE+9EYMPlygeXiMB1D++86ccIuA9pQb9HZgXuQM4Ed/FFl+pvUVb9ClVKoEl1WmYuULMKWRoM6xXWVQMOghmX4px85tgm9S71A0agOZVkfOGKq14e8KiH9ZlykvemSVodxUV0+KVFYKir0doRzGE9BVV/KO4+dCJeyg1mcg3BPPUgKJTa5uaAnJzG1P5XLXBOL0EVriC7LgTGfiYhlogAHSDF+hhigy/SzqGDevEptqH98T+XiVwfdpGV7JWUWg1e/4HygZeYd+es6JHbcVcrkYiKzDCanFx7Sp6GXB8Ttu0TNZp6qpfr1dJUKTVNTADQQ79N9pVJkgNs12gHA+EXIDUutca6S5LMerfM4PEXFUOPPoRzOPTU0XxJlMMcQj6a9W4YiilAuGHFZq/wB7TTPZ3PMJdS6bxvFQXPTpmFuDe4wEpk9qNYN4q4iN295c3OImpv0vMWEV8Rt9o0YgwJxHtD/wdpTB4i33UEbhiiBrqXj+YHBsUnSLwvyzmWzxDUGwCHsruPpa2ukd1qEIG5mEcbiU/D1faaxidxN1FnHon/DUKZYg1EgFC1MTnfebVUy1tlEW4PlC61A5m45hKmn0lblemz4cjMUHEXPpy8cS4ucRnHr4jslR9A9FOpuBNSpiuHoFyty6xucxh8LAsHAQdOJqCGYpFwQ9FmPpXrfJAdykrMInE3OITnEHhGbRm04Y8n1I/CFYBF6S2bWwcszjj0HjiXj0MYw/8ylRKlwlR1OZhN+vEqV7fBQudWdo49AhOJWb9bzFiGn/AMwJWJzAJzL9AlTM+0W3/HiFm41UC4hrPwELgB6LelYhLzCPFRl3OJUyRQit9XXor/ol+pDD69vSokMKhuUTvM2JeYy5boiJ/wDfc36BMI6nPoOY+oQseiqh6pBx/wA1Kms1669N+uiaJkl3OfU6opHLN59CMP8A9Y9Tc5hHEJzD0dR3Dc5zWaM0PEPQx1CPqehs9A+hD0IfVzHfrx6MJxP59TG//v8A/9oADAMBAAIAAwAAABD777z77777775+9lcZQwsqv4UVVy/777777zz777777z777777xlrh1KIhi59KyASodzh77777zz777777z777774GDuxD7WWFFf5iUPmZ2/77777zz777777z77777gl1rq3eLd6ImhOcPJl4j77777zz777777z77776oik9W43Q/rbybt/0YTCxz7777zz777777z77775KG0/wEPfMkKUvQjygl5r77777zz777777z77774KHJP6CZVNijAGuASFEaub7777zz777777z77776KKTYohxULr6ZIuo1jYhXb7777zz777777z77776BrzE/F8ixUoOHVGLC6CTb7777zz777777z77775vAGFkAmrE0jp5Tezl9OZb7777zz777777z77775GJQbViJE7rfQ+mGVqtyT77777zz777777z77777gktTem0fCRE9C18foNHf77777zz777777z777777Sdv0M9tN+UlLr65fnL777777zz777777z7zzzzzwNMmmWwxbhQLLSUP3z777777zz777777z7zzzzzzy32Esoqr2hQRPUGj7777777zz777777z7zzzzzzxyln9H0coMS5DDSD7777777zz777777z7zzzzz76yfH8cmzlwVC2DFX7777777zz777777z7zzzzz77fgAaVPkKQ/QjTCDb777777zz777777z7zz7776d77VVUMcYnjmGx1jT777777zz777777z7777yx6/2co4f3gy4jRyDgijb77777zz777777z776uy+FHnYkm/z7zBhCAzgzXFX7777zz777774Q7qR8b0fcHorpONDCMQAQkhQiVlXH76zT777//EACMRAQEB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\" width=\"622\" height=\"345\">\n" +
                "</figure>\n" +
                "<figure class=\"image image_resized\" style=\"width:27.8%;\">\n" +
                "    <img style=\"aspect-ratio:1080/1080;\" src=\"https://d24ovhgu8s7341.cloudfront.net/uploads/post/cover/3088/image2.png\" width=\"1080\" height=\"1080\">\n" +
                "    <figcaption>\n" +
                "        DALL-E/Every illustration\n" +
                "    </figcaption>\n" +
                "</figure>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    &nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    <strong>Elevate your performance with mindfulness</strong>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    Don't miss out on early bird pricing for <a href=\"http://hiddendiscipline.xyz/\">The Hidden Discipline</a>—ending Friday, May 3.\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    This eight-week mindfulness course is designed specifically for busy, high-achieving individuals looking to unlock the \"hidden discipline\" that can transform their lives. Through live sessions, daily practice, and expert guidance, you'll gain the tools to:\n" +
                "</p>\n" +
                "<ul>\n" +
                "    <li>\n" +
                "        <p style=\"margin-left:0px;\">\n" +
                "            Power through challenges with greater resilience\n" +
                "        </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "        <p style=\"margin-left:0px;\">\n" +
                "            Handle stress with ease and clarity\n" +
                "        </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "        <p style=\"margin-left:0px;\">\n" +
                "            Fully savor life's best moments\n" +
                "        </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "        <p style=\"margin-left:0px;\">\n" +
                "            Be more present and effective in every area of your life\n" +
                "        </p>\n" +
                "    </li>\n" +
                "</ul>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    For a limited time, Every subscribers can enroll for just <strong>$699—a 30 percent savings</strong>—by using code “<a href=\"https://buy.stripe.com/cN27sGdfL6dHbugfYY?prefilled_promo_code=EARLYBIRD\">EARLY BIRD</a>” at checkout. But hurry—this offer ends on Friday, May 3.\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    <strong>Take advantage of early bird pricing and unlock your full potential with </strong><a href=\"http://hiddendiscipline.xyz/\"><strong>The Hidden Discipline</strong></a><strong> today.</strong>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    Want to sponsor Every? <a href=\"https://www.passionfroot.me/every\">Click here</a>.\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    <a href=\"https://www.passionfroot.me/every\">Want to sponsor Every? Click here</a><span style=\"color:rgb(140,141,145)!important;\">.</span>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    <i>Was this newsletter forwarded to you? </i><a href=\"https://every.to/account\"><i>Sign up</i></a><i> to get it in your inbox.</i>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    &nbsp;\n" +
                "</p>\n" +
                "<hr>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    &nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    Over the past few weeks, new AI-powered hardware has been released to less-than-kind receptions—the Humane Pin was <a href=\"https://www.youtube.com/watch?v=TitZV6k8zfA&amp;pp=ygUTaHVtYW5lIGFpIHBpbiBta2JoZA%3D%3D\">lampooned</a> and the Rabbit R1 was <a href=\"https://www.youtube.com/watch?v=ddTV12hErTc&amp;t=1013s&amp;pp=ygUPcmFiYml0IHIxIG1rYmhk\">skewered</a>. While some people enjoyed the devices, it is safe to say these were not the launches the companies had hoped for. At the same time, other startups have raised <a href=\"https://www.fastcompany.com/91007630/avi-schiffmanns-tab-ai-necklace-has-raised-1-9-million-to-replace-god\">millions</a> in venture capital to build new consumer hardware devices. Investors I know are actively looking to deploy money into the category, and Sam Altman and Jony Ive are in talks to raise up to <a href=\"https://www.theinformation.com/articles/jony-ive-and-sam-altmans-ai-device-startup-in-funding-talks-with-emerson-thrive?rc=yrmswp\">a billion dollars</a> for a consumer hardware device.&nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    This disparity raises so many questions: Why are these new devices being received so poorly? What do founders and investors believe they’ll get by betting their careers on this difficult and clearly uphill battle? Will their bets actually work?&nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    To figure out the answers, I’ve been in the weeds with founders, talking product roadmaps, capital strategies, and levels of excitement. Here’s what I learned.\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    <strong>Why is this hardware’s moment?&nbsp;</strong>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    The reasoning behind the rapid new launches and investor bets is simple. It’s AI.&nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    Many see AI as a technological paradigm shift akin to jumping from personal computers to mobile computing—and there is a chance that a new Apple could be built. With Palo Alto’s favorite fruit-centric company currently enjoying a $2.68 trillion market capitalization, people are feeling like Louis Armstrong did when he touched a trumpet for the first time (quite jazzed).\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    I’ve previously <a href=\"https://every.to/napkin-math/ai-and-the-vision-pro-don-t-need-a-killer-app\">argued</a> the components of a hardware device can be broken down into three groups:&nbsp;&nbsp;\n" +
                "</p>\n" +
                "<ol>\n" +
                "    <li>\n" +
                "        <p style=\"margin-left:auto;\">\n" +
                "            <strong>Silicon: </strong>The chips running the computation for the device\n" +
                "        </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "        <p style=\"margin-left:auto;\">\n" +
                "            <strong>Interface: </strong>How you, as a user, interact with the device&nbsp;\n" +
                "        </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "        <p style=\"margin-left:auto;\">\n" +
                "            <strong>Sensors: </strong>The instruments providing data to the software running on a device—cameras, accelerometers, a GPS, a heartbeat sensor, etc.&nbsp;\n" +
                "        </p>\n" +
                "    </li>\n" +
                "</ol>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    New consumer devices are emerging because AI allows you to use the sensors, silicon, and interfaces developed for smartphones in novel ways. AI can take the <i>input</i> of large amounts of ambient data, such as the audio from your conversations and your behavior as you use your computer. Then, AI can <i>output</i> unique insights based on that corpus of data—much more personalized, sensitive, and accurate than what regular software can do today. It can also leverage existing data for new actions, such as following voice commands more closely. Basically, it’ll listen to what you say and bring out smart stuff that you missed.\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    While my description may be banal, the possibilities are exciting. Smartphones are dominant, but aren’t perfect. Consumer addiction is—at least in my estimation—largely due to the monetization of smartphone app stores and the form factor of the device. If AI can evolve our relationships with devices, it’s a meaningful change. An AI ambiently crunching data and performing tasks without the distraction of a screen is net good for humanity. And, as a happy capitalistic coincidence, it would probably make the inventor of said device the owner of several large yachts.&nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    So if the promise is so wonderful, why is the category so challenging?\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    <strong>The problem of the iPhone</strong>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    The answer is, once again, simple: The iPhone is too damn good.&nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    If I asked you to visualize what the iPhone disrupted, you’d probably think of an image like this—a brick cell phone, a digital camera, a GPS.\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    &nbsp;\n" +
                "</p>\n" +
                "<figure class=\"image\">\n" +
                "    <a href=\"https://d24ovhgu8s7341.cloudfront.net/uploads/editor/posts/3088/optimized_SC1we5cX-uWka7ScCC8HZcHqBwo9hd6-no7-9OvpWKjBj_YYQFOkGepSC0HSQnmI6zVUJaF-I9KdW4A3oICOSeyfigSu7V7PJDGm4n0vr0KYwaH-O6NN1oL31XdhePdG6iH-EGySaHjLePTcmAqWT44.png?link=true\"><img style=\"aspect-ratio:1200/800;\" src=\"https://d24ovhgu8s7341.cloudfront.net/uploads/editor/posts/3088/optimized_SC1we5cX-uWka7ScCC8HZcHqBwo9hd6-no7-9OvpWKjBj_YYQFOkGepSC0HSQnmI6zVUJaF-I9KdW4A3oICOSeyfigSu7V7PJDGm4n0vr0KYwaH-O6NN1oL31XdhePdG6iH-EGySaHjLePTcmAqWT44.png\" width=\"1200\" height=\"800\"></a>\n" +
                "</figure>\n" +
                "<p style=\"margin-left:auto;\">\n" +
                "    <i>Source: </i><a href=\"https://www.cnet.com/pictures/apple-iphone-and-the-gadgets-it-laid-to-rest/\"><i>CNET</i></a><i>.</i>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    &nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    Apple’s disruption happened because <i>it brought the sensors in all these devices into one form factor. </i>Since the iPhone had a powerful chip and a multi-touch screen interface, it became a catch-all device that does pretty much everything pretty well. Sure, it isn’t perfect—the Kindle exists—but the smartphone is so incredible because the interface is flexible enough that it can do a good enough job at essentially everything a consumer needs.&nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    &nbsp;\n" +
                "</p>\n" +
                "<figure class=\"image\">\n" +
                "    <a href=\"https://d24ovhgu8s7341.cloudfront.net/uploads/editor/posts/3088/optimized_VTZEpwe95SFE_c8qJyjkEldw9y71qOn2M12XldJ-aqVn_1Pg3a1HWxieRpEeVI3_lD-7dxI4Uv2YrtwO8wrKcuTpCsi5LWqF-fUeLxYrHq-gjtdyKm2cU_ll8RUk4o2IMtq402UuALQsZ9wlqIcO0Ao.png?link=true\"><img style=\"aspect-ratio:1400/846;\" src=\"https://d24ovhgu8s7341.cloudfront.net/uploads/editor/posts/3088/optimized_VTZEpwe95SFE_c8qJyjkEldw9y71qOn2M12XldJ-aqVn_1Pg3a1HWxieRpEeVI3_lD-7dxI4Uv2YrtwO8wrKcuTpCsi5LWqF-fUeLxYrHq-gjtdyKm2cU_ll8RUk4o2IMtq402UuALQsZ9wlqIcO0Ao.png\" width=\"1400\" height=\"846\"></a>\n" +
                "</figure>\n" +
                "<p style=\"margin-left:auto;\">\n" +
                "    <i>Source: Every illustration.</i>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    &nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    Apple has spent 18 years and hundreds of billions of dollars stuffing ever-better components into ever-more-powerful phones. Its market dominance has yielded a supply chain and manufacturing partners that a startup can’t hope to match.&nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    An AI hardware company is in the unenviable position of having inferior sensors than Apple, generic silicon chips, and a grand total of zero developers in its ecosystem. It is very, very hard to compete with an iPhone when every piece of hardware you have access to is worse. So these companies have to try to innovate on the interface. My current favorite AI hardware device, the Meta Ray-Bans, eschew multi-touch glass in favor of voice activation. The R1 has a scroll wheel, and Humane uses a laser projector.&nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    When you’re playing with these odds, your best bet is to differentiate on the software—not the hardware. Which means that, basically, most consumer AI hardware should just be an app. One founder told me as much: “My company should probably just be an app, but hardware sounded more fun.” While this statement may be true, customers do not particularly care about how good of a time a CEO is having.&nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    And if AI is what has gotten the market so excited, it should be the competitive edge, too. Both Rabbit and Humane’s products have been reviewed so critically because while the hardware is beautiful, they are less powerful and generalizable than the smartphone. And in both devices, the redeeming factor of AI doesn’t actually work—large language models are not good enough yet to be virtual assistants. The hardware was rushed to market before the AI was ready. As popular reviewer Marques Brownlee, aka MKBHD, <a href=\"https://twitter.com/MKBHD/status/1785102259740667960\">said</a>:\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    “This is the pinnacle of a trend that's been annoying for years: Delivering barely finished products to win a ‘race’ and then continuing to build them after charging full price. Games, phones, cars, now AI in a box.”\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    But if we wait for the AI to be “ready,” we’ll run into more problems. It is incredibly expensive and challenging to differentiate on the basis of AI models. Like I just <a href=\"https://every.to/napkin-math/the-ai-wars-have-begun\">wrote</a>, open-source can now provide GPT-4-levels of performance, so startups have to train a model to do something no other open-source model can—adding scientific research to the many hurdles that they’re trying to conquer.&nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    <strong>What happens next with AI hardware?</strong>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    To be clear, I very much want these companies to succeed. As a red-blooded capitalist, I love competition. To compete with Apple, AI hardware companies have three paths:\n" +
                "</p>\n" +
                "<ol>\n" +
                "    <li>\n" +
                "        <p style=\"margin-left:auto;\">\n" +
                "            <strong>Get weird with it.</strong> Explore use cases that are fundamentally different from smartphones. By focusing on markets where phones have proven ineffectual, such as healthcare or manufacturing, companies can carve out a space for themselves. Experimenting with unconventional interfaces like brain-computer interfaces, haptics, or augmented reality could also help them stand out.&nbsp;\n" +
                "        </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "        <p style=\"margin-left:auto;\">\n" +
                "            <strong>Go screen-free. </strong>Develop AI-powered devices that primarily rely on voice interactions, such as the Meta Ray-Bans. Ironically, Humane may have been right on this count.&nbsp;\n" +
                "        </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "        <p style=\"margin-left:auto;\">\n" +
                "            <strong>Rely on the phone. </strong>Rather than trying to replace smartphones entirely, focus on using the smartphone’s silicon and interface, with a hardware component acting as a supplemental sensor to gather data. <a href=\"https://www.fastcompany.com/91007630/avi-schiffmanns-tab-ai-necklace-has-raised-1-9-million-to-replace-god\">Tab</a> and <a href=\"https://www.limitless.ai/\">Limitless</a> are taking this approach: They sell an extra sensor, but the software lives in the cloud, and the hardware requires a connection to a phone via Bluetooth.&nbsp;\n" +
                "        </p>\n" +
                "    </li>\n" +
                "</ol>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    Venture-backed startups have an over 90 percent failure rate. These companies' struggles and long odds are a feature, not a bug. We should be cheering for every single founder trying something new. There is a viable path! But it requires something wholly new and different. Startups doing the same-old end up with the same-old result—failure.&nbsp;\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    &nbsp;\n" +
                "</p>\n" +
                "<hr>\n" +
                "<p style=\"margin-left:auto;\">\n" +
                "    <i><strong>Evan Armstrong</strong> is the lead writer for Every, where he writes the </i><a href=\"https://every.to/napkin-math\"><i>Napkin Math</i></a><i> column. You can follow him on X at </i><a href=\"https://twitter.com/itsurboyevan\"><i>@itsurboyevan</i></a><i> and on </i><a href=\"https://www.linkedin.com/in/evan-armstrong-701bb792/\"><i>LinkedIn</i></a><i>, and Every on X at </i><a href=\"https://twitter.com/every\"><i>@every</i></a><i> and on </i><a href=\"https://www.linkedin.com/company/everyinc/\"><i>LinkedIn</i></a><i>.</i>\n" +
                "</p>\n" +
                "<p style=\"margin-left:0px;\">\n" +
                "    &nbsp;\n" +
                "</p>");
        htmlContent.replace("${message}","this is message");
        message.setContent(htmlContent,"text/html;charset=utf-8");
        javaMailSender.send(message);
    }
    public String readFile(String filePath) throws IOException {
        Path path= Paths.get(filePath);
        return Files.readString(path, StandardCharsets.UTF_8);
    }
}
