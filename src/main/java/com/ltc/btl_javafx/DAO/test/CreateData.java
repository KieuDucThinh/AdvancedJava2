/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltc.btl_javafx.DAO.test;

import com.ltc.btl_javafx.DAO.MemberFunctionImpl;
import com.ltc.btl_javafx.application.Support;
import com.ltc.btl_javafx.model.Member;
import com.ltc.btl_javafx.model.Tenant;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import javafx.scene.control.Alert;

/**
 *
 * @author TUF
 */
public class CreateData {

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("001", "Thành phố Hà Nội");
        hashMap.put("002", "Hà Giang");
        hashMap.put("004", "Cao Bằng");
        hashMap.put("006", "Bắc Kạn");
        hashMap.put("008", "Tuyên Quang");
        hashMap.put("110", "Lào Cai");
        hashMap.put("111", "Điện Biên");
        hashMap.put("112", "Lai Châu");
        hashMap.put("114", "Sơn La");
        hashMap.put("115", "Yên Bái");
        hashMap.put("117", "Hòa Bình");
        hashMap.put("119", "Thái Nguyên");
        hashMap.put("220", "Lạng Sơn");
        hashMap.put("222", "Quảng Ninh");
        hashMap.put("224", "Bắc Giang");
        hashMap.put("225", "Phú Thọ");
        hashMap.put("226", "Vĩnh Phúc");
        hashMap.put("227", "Bắc Ninh");
        hashMap.put("330", "Hải Dương");
        hashMap.put("331", "Thành phố Hải Phòng");
        hashMap.put("333", "Hưng Yên");
        hashMap.put("334", "Thái Bình");
        hashMap.put("335", "Hà Nam");
        hashMap.put("336", "Nam Định");
        hashMap.put("337", "Ninh Bình");
        hashMap.put("338", "Thanh Hóa");
        hashMap.put("440", "Nghệ An");
        hashMap.put("442", "Hà Tĩnh");
        hashMap.put("444", "Quảng Bình");
        hashMap.put("445", "Quảng Trị");
        hashMap.put("446", "Thừa Thiên-Huế");
        hashMap.put("448", "Thành phố Đà Nẵng");
        hashMap.put("449", "Quảng Nam");
        hashMap.put("551", "Quảng Ngãi");
        hashMap.put("552", "Bình Định");
        hashMap.put("554", "Phú Yên");
        hashMap.put("556", "Khánh Hòa");
        hashMap.put("558", "Ninh Thuận");
        hashMap.put("660", "Bình Thuận");
        hashMap.put("662", "Kon Tum");
        hashMap.put("664", "Gia Lai");
        hashMap.put("666", "Đắk Lắk");
        hashMap.put("667", "Đắc Nông");
        hashMap.put("668", "Lâm Đồng");
        hashMap.put("770", "Bình Phước");
        hashMap.put("772", "Tây Ninh");
        hashMap.put("777", "Bình Dương");
        hashMap.put("775", "Đồng Nai");
        hashMap.put("777", "Bà Rịa-Vũng Tàu");
        hashMap.put("779", "Thành Phố Hồ Chí Minh");
        hashMap.put("880", "Long An");
        hashMap.put("882", "Tiền Giang");
        hashMap.put("883", "Bến Tre");
        hashMap.put("884", "Trà Vinh");
        hashMap.put("886", "Vĩnh Long");
        hashMap.put("887", "Đồng Tháp");
        hashMap.put("889", "An Giang");
        hashMap.put("991", "Kiên Giang");
        hashMap.put("992", "Thành phố Cần Thơ");
        hashMap.put("993", "Hậu Giang");
        hashMap.put("994", "Sóc Trăng");
        hashMap.put("995", "Bạc Liêu");
        hashMap.put("996", "Cà Mau");

        String[] maTP = {
            "002", "004", "006", "008", "110", "111", "112", "114", "115", "117", "119",
            "220", "222", "224", "225", "226", "227", "330", "331", "333", "334", "335",
            "336", "337", "338", "440", "442", "444", "445", "446", "448", "449", "551",
            "552", "554", "556", "558", "660", "662", "664", "666", "667", "668", "770",
            "772", "777", "775", "779", "880", "882", "883", "884", "886", "887", "889",
            "991", "992", "993", "994", "995", "996", "001"
        };

        String[] mobileNumbers = {
            "032", "033", "034", "035", "036",
            "037", "038", "039", "096", "097",
            "098", "086", "083", "084", "085",
            "081", "082", "088", "091", "094",
            "070", "079", "077", "076", "078",
            "090", "093", "089", "056", "058",
            "092", "059", "099"
        };

        String[] firstName = {"Bùi", "Đặng", "Phí", "Trần", "Hoàng",
            "Lê", "Nguyễn", "Phạm", "Đinh", "Lương",
            "Đào", "Hoàng", "Đàm", "Ngô", "Vũ",
            "Cao", "Kiều", "Đỗ", "Phan", "Lại",
            "Đồng", "Đoàn", "Hàn", "Bùi", "Quàng",};
        String[] middleName1 = {"Xuân", "Trọng", "Duy", "Văn", "Mạnh",
            "Hùng", "Thành", "Hữu", "Minh", "Huy",
            "Gia", "Doãn", "Công", "Đức", "Thế"};
        String[] middleName2 = {"Thị", "Ngọc", "Kim", "Tú", "Thanh", "Thảo"};
        String[] lastName1 = {"An", "Bằng", "Bộ", "Cường", "Đông",
            "Đức", "Dũng", "Duy", "Hải", "Hiệp",
            "Hiếu", "Hoàn", "Hoàng", "Hùng", "Hưng",
            "Huy", "Kiên", "Lâm", "Long", "Minh",
            "Nam", "Nguyên", "Ninh", "Quân", "Quang",
            "Quý", "Sơn", "Thắng", "Thành", "Thanh",
            "Thịnh", "Thưởng", "Tiến", "Trung", "Trường",
            "Tuấn", "Vinh", "Việt", "Vũ"};
        String[] lastName2 = {"Bích", "Bình", "Dung", "Hà", "Hương",
            "Huyền", "Linh", "Liên", "Ngân", "Ngọc",
            "Nguyệt", "Nhung", "Phương", "Thảo", "Thúy",
            "Trang", "Yến"};

        String[] maK = {
            "K452490",
            "K582468",
            "K406947",
            "K262607",
            "K641218",
            "K789185",
            "K422531",
            "K098544",
            "K108727",
            "K583459",
            "K347564",
            "K968472",
            "K185335",
            "K708498",
            "K677506"
        };

        //Chuoi
        String string1 = "";
        String cccd = "";

        //Khoi tao thoi gian
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.FRENCH);
        LocalDate localDate;

        Member member = new Member();
        Tenant tenant = new Tenant();
        //Member m = new Member(string1, tenant, string1, string1, LocalDate.MIN, string1, string1, string1);
        //Random
        int index1;
        int index2;
        int index3;
        try {
            // Tạo một đối tượng FileOutputStream để ghi dữ liệu vào file "output.txt"
            FileOutputStream fileOutputStream = new FileOutputStream("E:\\output.txt");

            // Tạo một đối tượng OutputStreamWriter để ghi dữ liệu dưới dạng ký tự
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, "UTF-8");
            index1 = (int) (Math.random() * 5 + 1);
            int count = 0;
            do {
                for (int j = 1; j <= index1; j++) {
                    //Set 3 so dau cccd
                    index1 = (int) (Math.random() * maTP.length);
                    cccd += maTP[index1];

                    //8. Set que
                    string1 = hashMap.get(maTP[index1]);
                    member.setPlaceOrigin(string1); //giai phong string1

                    index1 = (int) (Math.random() * 100);   //= random gioi tinh
                    index2 = (int) (Math.random() * 100); // = year

                    //Set so [4]
                    if (index2 > 24) {
                        cccd += (index1 % 2 == 0) ? 0 : 1;
                    } else {
                        cccd += (index1 % 2 == 0) ? 2 : 3;
                    }

                    //4. Set gioi tinh
                    string1 = (index1 % 2 == 0) ? "Nam" : "Nữ"; //giu index1 lai
                    member.setSex(string1); //giai phong string1

                    //Set so [56]
                    if (index2 < 10) {
                        cccd += "0" + index2; //=year 
                    } else {
                        cccd += index2;
                    }

                    // giu index2 lai
                    //Set 6 so cuoi CCCD + maTV
                    string1 = "TV";
                    for (int i = 1; i <= 6; i++) {
                        index3 = (int) (Math.random() * 10);
                        cccd += index3;
                        index3 = (int) (Math.random() * 10);
                        string1 += index3; //=memberID
                    }

                    //1 + 6 Set ID va cccd
                    member.setMemberID(string1);
                    member.setCitizenID(cccd);

                    //cccd, string1 co the dung
                    //index1, index2=year khong dung
                    //dung index3
                    //3. Set ten
                    index3 = (int) (Math.random() * firstName.length);
                    string1 = firstName[index3];  //=ho
                    if (index1 % 2 == 0) {            //nam
                        index3 = (int) (Math.random() * middleName1.length);
                        string1 += " " + middleName1[index3]; //=ten dem
                        index3 = (int) (Math.random() * lastName1.length);
                        string1 += " " + lastName1[index3]; //=ten
                    } else {                        //nu
                        index3 = (int) (Math.random() * middleName2.length);
                        string1 += " " + middleName2[index3]; //=ten dem
                        index3 = (int) (Math.random() * lastName2.length);
                        string1 += " " + lastName2[index3]; //=ten
                    }
                    member.setName(string1); //giai phong string1, index1
                    string1 = "";
                    
                    //7. Set sdt
                    if ((index2 < 10) || (index2 > 24)) {
                        index1 = (int) (Math.random() * mobileNumbers.length);
                        string1 = mobileNumbers[index1];
                        for (int i = 1; i <= 7; i++) {
                            index1 = (int) (Math.random() * 10);
                            string1 += index1;
                        }
                        member.setPhoneNum(string1);
                    }
                    string1 = "";//giai phong string1

                    //cccd, string1 co the dung
                    //index2=year khong dung
                    //dung index1, index3
                    //5. Set ngay sinh
                    index1 = (int) (Math.random() * 28 + 1);
                    if (index1 < 10) {
                        string1 = "0" + index1 + "/";
                    } else {
                        string1 = index1 + "/";
                    }

                    index1 = (int) (Math.random() * 12 + 1);
                    if (index1 < 10) {
                        string1 += "0" + index1 + "/";
                    } else {
                        string1 += index1 + "/";
                    }

                    if (index2 < 24) {
                        index2 += 2000;
                    } else {
                        index2 += 1900;
                    }
                    string1 += index2;
                    localDate = LocalDate.parse(string1, formatter);
                    member.setBirthdate(localDate);

                    tenant.setTenantID(maK[count]);
                    member.setTenant(tenant);
                    //MemberFunctionImpl.getInstance().addMember(member);
                    System.out.println(member);

                    // Chuỗi dữ liệu bạn muốn ghi vào file
                    String data = member.toString() + "\n";

                    // Ghi dữ liệu vào file
                    writer.write(data);

                    string1 = "";
                    cccd = "";
                }
                index1 = (int) (Math.random() * 5 + 1);
                count++;
            } while (count<maK.length-1); 
            writer.close();// Đóng luồng ghi

            System.out.println("Dữ liệu đã được ghi vào file.");
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
