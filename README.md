# Ứng dụng Quản lý Chung Cư Mini

## Giới thiệu

Ứng dụng Quản lý Chung Cư Mini là một hệ thống phần mềm được phát triển để hỗ trợ người quản trị trong việc quản lý các hoạt động liên quan đến chung cư mini. Ứng dụng cung cấp các chức năng cần thiết để quản lý tòa nhà, phòng, dịch vụ, hóa đơn, khách thuê, thành viên và thống kê doanh thu, số lượng khách.

## Công nghệ sử dụng

* **Frontend:** JavaFX
* **Backend:** Java
* **Kết nối cơ sở dữ liệu:** JDBC (Java Database Connectivity)
* **Cơ sở dữ liệu:** MySQL
* **Xuất báo cáo Excel:** Apache POI
* **Tạo báo cáo PDF:** JasperReports

## Các chức năng chính

Ứng dụng cung cấp các use case sau cho người quản trị:

1.  **Đăng nhập:** Cho phép người quản trị đăng nhập vào hệ thống bằng tài khoản quản trị.

2.  **Quản lý tòa nhà:**
    * Xem thông tin của các tòa nhà.
    * Thêm thông tin tòa nhà mới.
    * Sửa thông tin tòa nhà hiện có.
    * Xóa thông tin tòa nhà.

3.  **Quản lý phòng:**
    * Xem thông tin của các phòng.
    * Thêm thông tin phòng mới.
    * Sửa thông tin phòng hiện có.
    * Xóa thông tin phòng.
    * Lọc thông tin phòng theo các tiêu chí.
    * Tìm kiếm thông tin phòng theo từ khóa.

4.  **Quản lý dịch vụ:**
    * Xem danh sách các dịch vụ.
    * Thêm dịch vụ mới.
    * Sửa thông tin dịch vụ hiện có.
    * Xóa dịch vụ.

5.  **Quản lý hóa đơn:**
    * Xem danh sách các hóa đơn.
    * Thêm hóa đơn mới.
    * Sửa thông tin hóa đơn hiện có.
    * Xóa hóa đơn.

6.  **Xuất báo cáo khách thuê và thành viên và xem thống kê số lượng khách:**
    * Tìm kiếm thông tin khách thuê.
    * Xuất danh sách khách thuê ra file Excel.
    * Tìm kiếm thông tin thành viên.
    * Xuất danh sách thành viên ra file Excel.
    * Xem biểu đồ thống kê số lượng khách mới theo các tháng trong năm.
    * Xem biểu đồ thống kê tổng số lượng khách theo các tháng trong năm.

7.  **Tìm kiếm phòng:**
    * Tìm kiếm phòng theo tòa nhà.
    * Tìm kiếm phòng theo các điều kiện cụ thể (ví dụ: diện tích, giá thuê, trạng thái).

8.  **Thêm khách thuê mới:**
    * Thêm thông tin khách thuê mới vào một phòng trống đã chọn.

9.  **Quản lý khách thuê và bảo trì thành viên:**
    * Xem thông tin chi tiết của khách thuê.
    * Sửa thông tin của khách thuê.
    * Xem thông tin của thành viên.
    * Thêm thành viên mới vào phòng đã thuê.
    * Sửa thông tin của thành viên.
    * Xóa thông tin của thành viên.
    * Xóa toàn bộ thông tin khách thuê và thành viên của một phòng đã thuê được chọn.

10. **Thống kê doanh thu:**
    * Xem tổng doanh thu từ việc cho thuê phòng theo tháng.
    * Xem tổng doanh thu từ việc cho thuê phòng theo năm.

## Cài đặt và sử dụng

1.  **Yêu cầu hệ thống:**
    * Hệ điều hành: (Windows, Linux, macOS)
    * Java Development Kit (JDK): Phiên bản (17 trở lên)
    * MySQL Server: Đã được cài đặt và cấu hình
    * (Các yêu cầu khác nếu có)

2.  **Các bước cài đặt:**
    * Tải xuống và cài đặt JDK (nếu chưa có).
    * Cài đặt MySQL Server và tạo database cho ứng dụng.
    * Cấu hình kết nối cơ sở dữ liệu trong ứng dụng (thông tin host, port, username, password, database name).
    * Cài đặt các thư viện hỗ trợ (nếu cần).
    * Chạy ứng dụng JavaFX.


## Kiến trúc hệ thống

* **Kiến trúc một lớp (Single-Tier):** Ứng dụng JavaFX chạy trực tiếp trên máy tính của người quản trị và giao tiếp trực tiếp với cơ sở dữ liệu MySQL được cài đặt trên cùng máy.

Logic nghiệp vụ và giao diện người dùng được tích hợp trong ứng dụng JavaFX. JDBC được sử dụng để thiết lập kết nối và thực hiện các truy vấn đến cơ sở dữ liệu MySQL. Các thư viện Apache POI và JasperReports được tích hợp để cung cấp chức năng xuất dữ liệu ra file Excel và tạo báo cáo PDF.


---
