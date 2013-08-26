package models;

import java.util.Vector;

/**
 *
 * @author WALL
 */
public class Text {
    public static int hearder= 0x29A0C3;
    public static int colorBlack= 0x000000;
    public static int colorWhite= 0xffffff;
    public static ATM Items= new ATM();
    public static int bienn;
    public static int[] setBanks = new int[26];
    public static boolean[] setgan={false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
    public static String setKhoangcach = "3";
    public static int languages = 0;
    public static String CaiDoDai = "3";
    public static String DaThemUaThich = "add favourites successfully!";
    public static boolean chonTat = false;
    public static int bank_adv_id = 1;
    public static String bank_adv_name = "AB BANK";
    public static int prov_adv = 1;
    public static String xoa="delete";
    public static String buttom_search3 = "Quận Ba Đình";
    public static String buttom_search2 = "Hà Nội";
   // public static String buttom_search1 = "BIDV";
    public static String lb_option1 = "English";
    public static String lb_option2 = "";
    //public static String ThongTin = "About";
    public static String YeuThich = "favourites";
    public static String caidat = "setting";
    public static String GioiThieu = "about";
    public static String TimATM = "find ATMs";
    public static String Tinh = "city / province";
    public static String QuanHuyen = "district";
    public static String NganHang = "bank";
    public static String GanBan = "near me";
    public static String Back = "back";
    public static String NgonNgu ="languages";  
    public static String KhoangCach ="distance (km)";  
    public static String XoaTatCa ="delete all";  
    public static String Ok= "OK";
    public static String Loi ="message";
    public static String ThongBaoLoi ="import distance not exceeding 100 km!";
    public static String BanDo= "map";
    public static String DanDuong= "direction";
    public static String XoaUaThich= "delete favourite";
    public static String ThemUaThich= "favourite";
     public static String TonTaiTrongUaThich= "already exist in the your favorite";
     public static String NganHangLienKet="league banks";
     //public static String ketQua="Results";
     public static String khongCoKqtQua="no results";
     //public static String khongCoKqtQua="no results";
     public static String Khongdung="enter malformed!";
     
     public static String delete = "are you sure delete?";
     public static String tDelete = "confirm";
     public static String txtYes = "YES";
     public static String txtNo = "NO";
     public static String listChong= "list selection is empty!";
     public static String done = "done";
   
    public static String khongTimThay= "not find your location";
    public static String CaiDat ="setting";
    
    public static boolean [] mangXoa;
    public static boolean [] NganHangLKet;
    
    public void InitNganHangLienKet(int size){
        NganHangLKet= new boolean[size];
        for (int i=0 ; i <size;i++ ){
            NganHangLKet[i]=false;
        }
    }
    public void InitMangXoa(int size){
        mangXoa= new boolean[size];
        for (int i=0 ; i <size;i++ ){
            mangXoa[i]=false;
        }
    }
    
    public void Languge(int languages) {
        if (languages == 0) {
            this.done= "done";
            this.listChong= "list selection is empty!";
            this.khongTimThay= "not find your location";
            this.DaThemUaThich = "add Favourites successfully!";
            this.khongCoKqtQua="no results";
            this.Khongdung="enter malformed!";
       //     this.ketQua= "Results";
            this.TonTaiTrongUaThich= "already exist in the your favourite";
            this.NganHangLienKet= "league banks";
            this.xoa= "delete";
            this.BanDo= "map";
            this.DanDuong="direction";
            this.XoaUaThich="delete favourite";
            this.ThemUaThich="favourite";
            this.XoaTatCa= "delete all";
            this.Ok= "OK";
            this.Loi= "message";
            this.ThongBaoLoi="import distance not exceeding 100 km!";
//            this.GanBan2=  "Near you";
//            this.NgonNgu2 ="Languages";
            this.lb_option1="English";
         //   this.ThongTin = "about";
            this.YeuThich = "favourites";
            this.caidat = "setting";
            this.GioiThieu = "about";
            this.TimATM = "find ATMs";
            this.GanBan = "near me";
            this.NganHang = "bank";
            this.Back = "back";
            this.Tinh = "city / province";
            this.QuanHuyen = "district";
            this.CaiDat ="setting";
            this.NgonNgu ="languages"; 
            this.KhoangCach ="distance (km)";
            
            this.delete = "are you sure delete?";
            this.tDelete = "confirm";
            this.txtNo = "NO";
            this.txtYes = "YES";
                          
        } else {
            this.done= "thực hiện";
            this.listChong= "bạn chưa lựa chọn";
            this.khongTimThay= "không tìm thấy vị trí của bạn";
            this.Khongdung="nhập không đúng định dạng!";
            this.DaThemUaThich = "đã thêm vào yêu thích!";
            this.khongCoKqtQua="không có kết quả";
         //   this.ketQua="Kết quả";
            this.NganHangLienKet="ngân hàng liên kết";
            this.TonTaiTrongUaThich= "đã tồn tại trong yêu thích";
            this.xoa= "xóa";
             this.BanDo= "bản đồ";
            this.DanDuong="dẫn đường";
            this.XoaUaThich="xóa yêu thích";
            this.ThemUaThich="yêu thích";
            this.XoaTatCa= "xóa tất cả";
            this.Ok= "CHẤP NHẬN";
            this.Loi= "thông báo";
            this.ThongBaoLoi="khoảng cách nhập không quá 100 km!";
            
            this.lb_option1="Tiếng Việt";
           // this.ThongTin = "thông tin";
            this.YeuThich = "yêu thích";
            this.caidat = "cài đặt";
            this.GioiThieu = "giới thiệu";
            this.TimATM = "tìm ATM";
            this.Tinh = "tỉnh";
            this.NganHang = "ngân hàng";
            this.GanBan = "gần bạn";
            this.Back = "trở về";
            this.Tinh = "tỉnh / thành phố";
            this.QuanHuyen = "quận / huyện";
            this.CaiDat ="cài đặt";
            this.NgonNgu ="ngôn ngữ";   
            this.KhoangCach ="khoảng cách (km)";
            
            this.delete = "bạn chắc chắn muốn xóa?";
            this.tDelete = "xác nhận";
            this.txtNo = "KHÔNG";
            this.txtYes = "CÓ";
        }

    }
}
