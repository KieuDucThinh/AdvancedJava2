-- --------------------------------------------------------
-- Máy chủ:                      127.0.0.1
-- Server version:               10.4.32-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Phiên bản:           12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for quanlychungcumini
CREATE DATABASE IF NOT EXISTS `quanlychungcumini` /*!40100 DEFAULT CHARACTER SET armscii8 COLLATE armscii8_bin */;
USE `quanlychungcumini`;

-- Dumping structure for table quanlychungcumini.dangnhap
CREATE TABLE IF NOT EXISTS `dangnhap` (
  `ID` varchar(20) NOT NULL,
  `MatKhau` varchar(45) NOT NULL DEFAULT '',
  `TenNguoiDung` varchar(45) NOT NULL DEFAULT '',
  `CapBac` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table quanlychungcumini.dangnhap: ~0 rows (approximately)
INSERT INTO `dangnhap` (`ID`, `MatKhau`, `TenNguoiDung`, `CapBac`) VALUES
	('1', 'Cao181203*', 'Lâm Thanh Cao', NULL),
	('2022600701', 'Thinh181204*', 'Kiều Đức Thịnh', NULL),
	('619122', 'e10adc3949ba59abbe56e057f20f883e', 'Kiều Đức Thịnh', NULL);

-- Dumping structure for table quanlychungcumini.dichvu
CREATE TABLE IF NOT EXISTS `dichvu` (
  `MaDV` varchar(10) NOT NULL DEFAULT '',
  `MaT` varchar(20) NOT NULL DEFAULT '',
  `TenDV` varchar(45) NOT NULL DEFAULT '',
  `LoaiDV` varchar(45) NOT NULL DEFAULT '',
  `GiaDV` decimal(20,0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`MaDV`),
  KEY `FK_dichvu_toanha` (`MaT`),
  CONSTRAINT `FK_dichvu_toanha` FOREIGN KEY (`MaT`) REFERENCES `toanha` (`MaT`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table quanlychungcumini.dichvu: ~2 rows (approximately)
INSERT INTO `dichvu` (`MaDV`, `MaT`, `TenDV`, `LoaiDV`, `GiaDV`) VALUES
	('D05', 'B', 'Điện', 'ĐIỆN', 4000),
	('DV01', 'A', 'Điện', 'ĐIỆN', 3000),
	('DV02', 'A', 'Nước', 'NƯỚC', 30000);

-- Dumping structure for table quanlychungcumini.hoadon
CREATE TABLE IF NOT EXISTS `hoadon` (
  `MaHD` int(11) NOT NULL DEFAULT 0,
  `ID` varchar(20) NOT NULL DEFAULT '',
  `MaT` varchar(20) NOT NULL DEFAULT '',
  `MaP` varchar(10) CHARACTER SET armscii8 COLLATE armscii8_bin NOT NULL DEFAULT '0',
  `MaK` varchar(10) CHARACTER SET armscii8 COLLATE armscii8_bin NOT NULL DEFAULT '',
  `SoDien` int(11) NOT NULL,
  `NgayXuatHD` date NOT NULL,
  `TongTien` decimal(20,0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`MaHD`),
  KEY `FK_hoadon_phong` (`MaP`),
  KEY `FK_hoadon_khachthue` (`MaK`),
  KEY `FK_hoadon_dangnhap` (`ID`),
  KEY `FK_hoadon_toanha` (`MaT`),
  CONSTRAINT `FK_hoadon_dangnhap` FOREIGN KEY (`ID`) REFERENCES `dangnhap` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_hoadon_khachthue` FOREIGN KEY (`MaK`) REFERENCES `khachthue` (`MaK`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_hoadon_phong` FOREIGN KEY (`MaP`) REFERENCES `phong` (`MaP`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_hoadon_toanha` FOREIGN KEY (`MaT`) REFERENCES `toanha` (`MaT`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table quanlychungcumini.hoadon: ~2 rows (approximately)
INSERT INTO `hoadon` (`MaHD`, `ID`, `MaT`, `MaP`, `MaK`, `SoDien`, `NgayXuatHD`, `TongTien`) VALUES
	(0, '2022600701', 'A', 'A02', 'K347564', 1, '2024-06-28', 7563000),
	(7777, '2022600701', 'B', 'B04', 'K108727', 5, '2024-06-28', 3520000);

-- Dumping structure for table quanlychungcumini.inhoadon
CREATE TABLE IF NOT EXISTS `inhoadon` (
  `MaHD` int(11) NOT NULL AUTO_INCREMENT,
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `MaT` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `MaP` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `MaK` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `SoDien` int(11) NOT NULL,
  `NgayXuatHD` date DEFAULT NULL,
  `TongTien` decimal(20,0) NOT NULL,
  PRIMARY KEY (`MaHD`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table quanlychungcumini.inhoadon: ~1 rows (approximately)
INSERT INTO `inhoadon` (`MaHD`, `ID`, `MaT`, `MaP`, `MaK`, `SoDien`, `NgayXuatHD`, `TongTien`) VALUES
	(4, '2022600701', 'A', 'A02', 'K347564', 1, '2024-06-28', 7563000);

-- Dumping structure for table quanlychungcumini.khachthue
CREATE TABLE IF NOT EXISTS `khachthue` (
  `MaK` varchar(10) CHARACTER SET armscii8 COLLATE armscii8_bin NOT NULL,
  `MaP` varchar(10) CHARACTER SET armscii8 COLLATE armscii8_bin NOT NULL,
  `MaT` varchar(20) NOT NULL,
  `TenK` varchar(40) NOT NULL,
  `GioiTinh` varchar(3) NOT NULL,
  `NgaySinh` date NOT NULL,
  `CCCD` varchar(12) CHARACTER SET armscii8 COLLATE armscii8_bin NOT NULL,
  `SDT` varchar(10) CHARACTER SET armscii8 COLLATE armscii8_bin NOT NULL,
  `Que` varchar(50) NOT NULL,
  `NgayThue` date NOT NULL,
  PRIMARY KEY (`MaK`),
  UNIQUE KEY `CCCD` (`CCCD`),
  UNIQUE KEY `SDT` (`SDT`),
  KEY `FK__phong` (`MaP`),
  KEY `FK_khachthue_toanha` (`MaT`),
  CONSTRAINT `FK__phong` FOREIGN KEY (`MaP`) REFERENCES `phong` (`MaP`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_khachthue_toanha` FOREIGN KEY (`MaT`) REFERENCES `toanha` (`MaT`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table quanlychungcumini.khachthue: ~15 rows (approximately)
INSERT INTO `khachthue` (`MaK`, `MaP`, `MaT`, `TenK`, `GioiTinh`, `NgaySinh`, `CCCD`, `SDT`, `Que`, `NgayThue`) VALUES
	('K098544', 'A05', 'A', 'Đỗ Thảo Linh', 'Nữ', '1990-06-02', '666190719253', '0781337275', 'Đắk Lắk', '2023-11-25'),
	('K108727', 'B04', 'B', 'Phí Hữu Quý', 'Nam', '1982-01-25', '558082487175', '0862043733', 'Ninh Thuận', '2024-01-01'),
	('K185335', 'B02', 'B', 'Phạm Văn Hưng', 'Nam', '1988-03-28', '668088859077', '0925114221', 'Lâm Đồng', '2024-05-17'),
	('K262607', 'B01', 'B', 'Quàng Văn Đông', 'Nam', '1961-07-01', '770061800510', '0596083395', 'Bình Phước', '2024-02-09'),
	('K347564', 'A02', 'A', 'Ngô Ngọc Dung', 'Nữ', '1999-11-25', '880199744251', '0882261300', 'Long An', '2024-08-08'),
	('K406947', 'C04', 'C', 'Đàm Thị Bình', 'Nữ', '1977-11-15', '992177173568', '0398501759', 'Thành phố Cần Thơ', '2023-10-06'),
	('K422531', 'A04', 'A', 'Cao Minh Hiệp', 'Nam', '1995-09-21', '008095008441', '0702360053', 'Tuyên Quang', '2023-10-06'),
	('K452490', 'A03', 'A', 'Hoàng Tú Bình', 'Nữ', '1953-02-26', '887153326968', '0377409966', 'Đồng Tháp', '2023-07-02'),
	('K582468', 'B06', 'B', 'Quàng Hùng Tuấn', 'Nam', '1971-08-07', '006071234994', '0396750849', 'Bắc Kạn', '2023-07-09'),
	('K583459', 'B03', 'B', 'Đoàn Thị Trang', 'Nữ', '1976-05-10', '995176147711', '0864773685', 'Bạc Liêu', '2023-11-07'),
	('K641218', 'C02', 'C', 'Đỗ Thảo Hương', 'Nữ', '2000-08-24', '887300301483', '0598574233', 'Đồng Tháp', '2024-01-17'),
	('K677506', 'A06', 'A', 'Hàn Hữu Long', 'Nam', '1981-04-17', '664081409001', '0998896251', 'Gia Lai', '2024-04-22'),
	('K708498', 'C03', 'C', 'Lại Tú Thảo', 'Nữ', '1995-03-20', '117195626509', '0943293758', 'Hòa Bình', '2023-08-21'),
	('K789185', 'C01', 'C', 'Bùi Duy Quý', 'Nam', '1986-07-11', '336086296013', '0599567739', 'Nam Định', '2024-02-15'),
	('K968472', 'B05', 'B', 'Cao Ngọc Thúy', 'Nữ', '1970-03-01', '444170786974', '0906982068', 'Quảng Bình', '2024-05-28');

-- Dumping structure for table quanlychungcumini.phong
CREATE TABLE IF NOT EXISTS `phong` (
  `MaP` varchar(10) CHARACTER SET armscii8 COLLATE armscii8_bin NOT NULL,
  `MaT` varchar(50) NOT NULL DEFAULT '',
  `LoaiP` varchar(50) NOT NULL DEFAULT '',
  `TrangThai` bit(1) NOT NULL DEFAULT b'0',
  `GiaP` decimal(20,0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`MaP`),
  KEY `FK_phong_toanha` (`MaT`),
  CONSTRAINT `FK_phong_toanha` FOREIGN KEY (`MaT`) REFERENCES `toanha` (`MaT`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table quanlychungcumini.phong: ~19 rows (approximately)
INSERT INTO `phong` (`MaP`, `MaT`, `LoaiP`, `TrangThai`, `GiaP`) VALUES
	('A01', 'A', '1PN 1PK', b'0', 5500000),
	('A02', 'A', '2PN', b'1', 7500000),
	('A03', 'A', '3PN', b'1', 12000000),
	('A04', 'A', '1PN', b'1', 3000000),
	('A05', 'A', '2PN 1PK', b'1', 10000000),
	('A06', 'A', '2PN', b'1', 8000000),
	('B01', 'B', '1PN 1PK', b'1', 5000000),
	('B02', 'B', '2PN', b'1', 7000000),
	('B03', 'B', '3PN', b'1', 15000000),
	('B04', 'B', '1PN', b'1', 3500000),
	('B05', 'B', '2PN 1PK', b'1', 9000000),
	('B06', 'B', '1PN', b'1', 3000000),
	('C01', 'C', '1PN', b'1', 4000000),
	('C02', 'C', '2PN', b'1', 12000000),
	('C03', 'C', '3PN', b'1', 14000000),
	('C04', 'C', '3PN 1PK', b'1', 20000000),
	('C05', 'C', '2PN 1PK', b'0', 11000000),
	('D00', 'D', 'Test', b'0', 3000000);

-- Dumping structure for table quanlychungcumini.thanhvien
CREATE TABLE IF NOT EXISTS `thanhvien` (
  `MaTV` varchar(10) CHARACTER SET armscii8 COLLATE armscii8_bin NOT NULL,
  `MaK` varchar(10) CHARACTER SET armscii8 COLLATE armscii8_bin NOT NULL DEFAULT '',
  `TenTV` varchar(40) NOT NULL DEFAULT '',
  `GioiTinh` varchar(3) NOT NULL DEFAULT '',
  `NgaySinh` date NOT NULL,
  `CCCD` varchar(12) CHARACTER SET armscii8 COLLATE armscii8_bin NOT NULL DEFAULT '',
  `SDT` varchar(10) CHARACTER SET armscii8 COLLATE armscii8_bin DEFAULT NULL,
  `Que` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`MaTV`),
  UNIQUE KEY `CCCD` (`CCCD`),
  KEY `FK__khachthue` (`MaK`),
  CONSTRAINT `FK__khachthue` FOREIGN KEY (`MaK`) REFERENCES `khachthue` (`MaK`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table quanlychungcumini.thanhvien: ~57 rows (approximately)
INSERT INTO `thanhvien` (`MaTV`, `MaK`, `TenTV`, `GioiTinh`, `NgaySinh`, `CCCD`, `SDT`, `Que`) VALUES
	('TV001140', 'K708498', 'Nguyễn Thảo Huyền', 'Nữ', '1994-05-02', '996194906412', '0585507898', 'Cà Mau'),
	('TV018035', 'K098544', 'Phí Thế Huy', 'Nam', '1982-07-24', '995082584237', '0816501149', 'Bạc Liêu'),
	('TV019704', 'K641218', 'Đặng Công Quang', 'Nam', '1974-03-24', '338074474353', '0912139823', 'Thanh Hóa'),
	('TV023977', 'K422531', 'Đồng Thanh Thúy', 'Nữ', '1961-05-02', '887161021800', '0881036830', 'Đồng Tháp'),
	('TV039692', 'K708498', 'Quàng Thảo Trang', 'Nữ', '2003-05-13', '993303913940', '0927061447', 'Hậu Giang'),
	('TV045740', 'K422531', 'Cao Văn Bộ', 'Nam', '1981-09-16', '554081405318', '0568863848', 'Phú Yên'),
	('TV074767', 'K789185', 'Nguyễn Thanh Nhung', 'Nữ', '1938-04-17', '117138572296', '0358692708', 'Hòa Bình'),
	('TV116589', 'K968472', 'Quàng Thảo Liên', 'Nữ', '1980-04-23', '331180411951', '0919510995', 'Thành phố Hải Phòng'),
	('TV119077', 'K582468', 'Đỗ Văn Thưởng', 'Nam', '1973-09-24', '227073584842', '0339674176', 'Bắc Ninh'),
	('TV167399', 'K262607', 'Cao Gia Thành', 'Nam', '1942-04-02', '882042669179', '0939591943', 'Tiền Giang'),
	('TV203038', 'K406947', 'Bùi Tú Nhung', 'Nữ', '1958-06-04', '552158209216', '0372370838', 'Bình Định'),
	('TV220147', 'K262607', 'Hàn Thành Hiếu', 'Nam', '1947-11-27', '445047190428', '0972376678', 'Quảng Trị'),
	('TV229839', 'K422531', 'Đàm Xuân Hoàn', 'Nam', '1947-06-03', '114047327164', '0566165880', 'Sơn La'),
	('TV254812', 'K406947', 'Cao Tú Phương', 'Nữ', '1999-10-02', '110199980329', '0391227039', 'Lào Cai'),
	('TV263714', 'K262607', 'Phạm Thanh Bình', 'Nữ', '1964-11-03', '336164697216', '0968595304', 'Nam Định'),
	('TV282459', 'K583459', 'Hàn Hữu Minh', 'Nam', '1948-04-09', '440048867477', '0854045763', 'Nghệ An'),
	('TV285390', 'K789185', 'Đàm Thảo Ngọc', 'Nữ', '1968-10-15', '225168571838', '0373120381', 'Phú Thọ'),
	('TV305684', 'K185335', 'Bùi Hữu Thành', 'Nam', '1931-12-11', '227031690103', '0784394013', 'Bắc Ninh'),
	('TV305925', 'K262607', 'Lương Xuân An', 'Nam', '1991-03-08', '008091212441', '0762015677', 'Tuyên Quang'),
	('TV313455', 'K789185', 'Vũ Thế Kiên', 'Nam', '1966-06-06', '334066474360', '0562074936', 'Thái Bình'),
	('TV318514', 'K098544', 'Bùi Doãn Sơn', 'Nam', '1933-08-05', '336033818465', '0964354817', 'Nam Định'),
	('TV322700', 'K347564', 'Hoàng Thảo Yến', 'Nữ', '1949-08-16', '112149552087', '0971813074', 'Lai Châu'),
	('TV369314', 'K406947', 'Đồng Thị Yến', 'Nữ', '1955-08-18', '993155682851', '0940362053', 'Hậu Giang'),
	('TV407239', 'K422531', 'Bùi Doãn Dũng', 'Nam', '1931-02-28', '887031919545', '0359696305', 'Đồng Tháp'),
	('TV452580', 'K406947', 'Kiều Văn Thắng', 'Nam', '1993-02-04', '775093654155', '0778159070', 'Đồng Nai'),
	('TV467335', 'K098544', 'Đỗ Kim Thảo', 'Nữ', '1948-05-19', '004148279112', '0329851707', 'Cao Bằng'),
	('TV468149', 'K708498', 'Lê Doãn Hoàn', 'Nam', '1956-04-25', '111056659814', '0389044535', 'Điện Biên'),
	('TV518503', 'K582468', 'Lương Ngọc Thảo', 'Nữ', '1929-10-04', '884129411164', '0947940768', 'Trà Vinh'),
	('TV518555', 'K708498', 'Lại Gia Tiến', 'Nam', '1944-02-03', '554044561279', '0832768842', 'Phú Yên'),
	('TV526472', 'K098544', 'Cao Xuân Nam', 'Nam', '1930-07-27', '115030573346', '0347538993', 'Yên Bái'),
	('TV547969', 'K583459', 'Ngô Ngọc Liên', 'Nữ', '1941-01-05', '995141121827', '0989286659', 'Bạc Liêu'),
	('TV551043', 'K262607', 'Phí Kim Trang', 'Nữ', '1950-11-09', '556150794268', '0350395882', 'Khánh Hòa'),
	('TV557808', 'K406947', 'Kiều Thảo Phương', 'Nữ', '1933-11-03', '448133699822', '0969352043', 'Thành phố Đà Nẵng'),
	('TV568842', 'K406947', 'Đinh Mạnh Tiến', 'Nam', '1957-12-17', '336057548137', '0810515378', 'Nam Định'),
	('TV570509', 'K406947', 'Hoàng Văn Hiệp', 'Nam', '2006-06-08', '445206089614', '0359822147', 'Quảng Trị'),
	('TV576988', 'K406947', 'Quàng Kim Dung', 'Nữ', '1983-12-28', '448183012170', '0938572833', 'Thành phố Đà Nẵng'),
	('TV597069', 'K641218', 'Lại Thành Bằng', 'Nam', '1965-02-28', '448065746744', '0359251091', 'Thành phố Đà Nẵng'),
	('TV602109', 'K422531', 'Nguyễn Thảo Thúy', 'Nữ', '1932-08-19', '222132948254', '0899928909', 'Quảng Ninh'),
	('TV627658', 'K422531', 'Đặng Công Sơn', 'Nam', '1996-11-13', '115096209437', '0561719005', 'Yên Bái'),
	('TV639559', 'K582468', 'Ngô Công Thắng', 'Nam', '1937-02-11', '558037492550', '0928929860', 'Ninh Thuận'),
	('TV702384', 'K968472', 'Bùi Thảo Huyền', 'Nữ', '2009-05-14', '227309025710', '0356754653', 'Bắc Ninh'),
	('TV714093', 'K582468', 'Vũ Thị Phương', 'Nữ', '1989-05-13', '770189231949', '0897620151', 'Bình Phước'),
	('TV730476', 'K452490', 'Hàn Mạnh Thành', 'Nam', '2018-01-24', '552218619122', NULL, 'Bình Định'),
	('TV758476', 'K968472', 'Cao Doãn Minh', 'Nam', '1985-05-16', '114085706848', '0911197612', 'Sơn La'),
	('TV763485', 'K582468', 'Vũ Thành Hiếu', 'Nam', '1991-10-05', '226091877532', '0567023336', 'Vĩnh Phúc'),
	('TV789718', 'K108727', 'Ngô Huy Hưng', 'Nam', '1951-01-25', '449051610588', '0884988080', 'Quảng Nam'),
	('TV859142', 'K968472', 'Vũ Công Sơn', 'Nam', '1936-05-15', '115036024024', '0829764156', 'Yên Bái'),
	('TV860933', 'K582468', 'Hoàng Thảo Bích', 'Nữ', '1931-05-20', '777131871372', '0351528813', 'Bà Rịa-Vũng Tàu'),
	('TV895120', 'K185335', 'Đinh Ngọc Nhung', 'Nữ', '1929-10-18', '558129864377', '0397988398', 'Ninh Thuận'),
	('TV906313', 'K582468', 'Đàm Doãn Dũng', 'Nam', '1933-07-11', '660033579706', '0337099879', 'Bình Thuận'),
	('TV940266', 'K789185', 'Đỗ Xuân Quý', 'Nam', '1980-03-24', '227080375755', '0861956861', 'Bắc Ninh'),
	('TV950576', 'K422531', 'Hoàng Hữu Thanh', 'Nam', '2001-05-07', '777201332882', '0838737686', 'Bà Rịa-Vũng Tàu'),
	('TV966894', 'K708498', 'Lương Kim Hà', 'Nữ', '1972-08-26', '004172548914', '0568261567', 'Cao Bằng'),
	('TV977215', 'K098544', 'Phan Minh Trung', 'Nam', '2004-10-23', '006204636464', '0908626510', 'Bắc Kạn'),
	('TV979715', 'K185335', 'Bùi Thị Ngọc', 'Nữ', '1982-03-08', '331182082202', '0346010132', 'Thành phố Hải Phòng');

-- Dumping structure for table quanlychungcumini.thongkehoadon
CREATE TABLE IF NOT EXISTS `thongkehoadon` (
  `STT` int(11) NOT NULL AUTO_INCREMENT,
  `NgayXuatHD` date NOT NULL,
  `TongTien` decimal(20,0) NOT NULL,
  PRIMARY KEY (`STT`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table quanlychungcumini.thongkehoadon: ~3 rows (approximately)
INSERT INTO `thongkehoadon` (`STT`, `NgayXuatHD`, `TongTien`) VALUES
	(1, '2024-06-27', 3500000),
	(2, '2024-06-28', 7563000),
	(3, '2024-06-28', 3520000);

-- Dumping structure for table quanlychungcumini.toanha
CREATE TABLE IF NOT EXISTS `toanha` (
  `MaT` varchar(20) NOT NULL DEFAULT '',
  `DiaChi` varchar(255) NOT NULL,
  PRIMARY KEY (`MaT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table quanlychungcumini.toanha: ~4 rows (approximately)
INSERT INTO `toanha` (`MaT`, `DiaChi`) VALUES
	('A', 'Ngõ 68, Đường Đình Quán, Phường Minh Khai, Bắc Từ Liêm, Hà Nội'),
	('B', 'Dự án Athena Complex, Phường Phương Canh, Nam Từ Liêm, Hà Nội'),
	('C', 'Dự án Hoàng Thành Pearl, Quận Nam Từ Liêm, Hà Nội'),
	('D', 'Số 298, đường Cầu Diễn, Phường Minh Khai, quận Bắc Từ Liêm, TP. Hà Nội');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
