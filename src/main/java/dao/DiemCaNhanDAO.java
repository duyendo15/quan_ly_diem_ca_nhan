package dao;

import model.DiemCaNhan;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiemCaNhanDAO {

    // Lấy tất cả điểm của một sinh viên
    public List<DiemCaNhan> findByMaSV(String maSV) {
        List<DiemCaNhan> ds = new ArrayList<>();
        String sql = "SELECT d.DiemID, d.MaSV, sv.HoTen, d.MaMH, m.TenMonHoc, m.SoTinChi, " +
                     "d.HocKy, d.DiemThuongXuyen, d.DiemGiuaKy, d.DiemCuoiKy, d.DiemTongKet " +
                     "FROM DiemCaNhan d " +
                     "JOIN SinhVien sv ON d.MaSV = sv.MaSV " +
                     "JOIN MonHoc m ON d.MaMH = m.MaMH AND d.MaSV = m.MaSV " +
                     "WHERE d.MaSV=? ORDER BY d.HocKy, d.MaMH";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSV);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // Lấy danh sách học kỳ của sinh viên
    public List<String> getHocKyByMaSV(String maSV) {
        List<String> ds = new ArrayList<>();
        String sql = "SELECT DISTINCT HocKy FROM DiemCaNhan WHERE MaSV=? ORDER BY HocKy";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSV);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(rs.getString("HocKy"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // Lấy điểm của sinh viên theo học kỳ
    public List<DiemCaNhan> findByMaSVAndHocKy(String maSV, String hocKy) {
        List<DiemCaNhan> ds = new ArrayList<>();
        String sql = "SELECT d.DiemID, d.MaSV, sv.HoTen, d.MaMH, m.TenMonHoc, m.SoTinChi, " +
                     "d.HocKy, d.DiemThuongXuyen, d.DiemGiuaKy, d.DiemCuoiKy, d.DiemTongKet " +
                     "FROM DiemCaNhan d " +
                     "JOIN SinhVien sv ON d.MaSV = sv.MaSV " +
                     "JOIN MonHoc m ON d.MaMH = m.MaMH AND d.MaSV = m.MaSV " +
                     "WHERE d.MaSV=? AND d.HocKy=? ORDER BY d.MaMH";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSV);
            ps.setString(2, hocKy);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // Lấy một bản ghi điểm theo ID và sinh viên (chỉ chủ sở hữu mới lấy được)
    public DiemCaNhan findByIdAndMaSV(int diemID, String maSV) {
        String sql = "SELECT d.DiemID, d.MaSV, sv.HoTen, d.MaMH, m.TenMonHoc, m.SoTinChi, " +
                     "d.HocKy, d.DiemThuongXuyen, d.DiemGiuaKy, d.DiemCuoiKy, d.DiemTongKet " +
                     "FROM DiemCaNhan d " +
                     "JOIN SinhVien sv ON d.MaSV = sv.MaSV " +
                     "JOIN MonHoc m ON d.MaMH = m.MaMH AND d.MaSV = m.MaSV " +
                     "WHERE d.DiemID=? AND d.MaSV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, diemID);
            ps.setString(2, maSV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm điểm mới
    public boolean insert(DiemCaNhan d) {
        String sql = "INSERT INTO DiemCaNhan(MaSV, MaMH, HocKy, DiemThuongXuyen, DiemGiuaKy, DiemCuoiKy, DiemTongKet) " +
                     "VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getMaSV());
            ps.setString(2, d.getMaMH());
            ps.setString(3, d.getHocKy());
            ps.setObject(4, d.getDiemThuongXuyen());
            ps.setObject(5, d.getDiemGiuaKy());
            ps.setObject(6, d.getDiemCuoiKy());
            ps.setObject(7, d.getDiemTongKet());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật điểm theo ID và sinh viên
    public boolean updateByIdAndMaSV(DiemCaNhan d) {
        String sql = "UPDATE DiemCaNhan SET MaMH=?, HocKy=?, DiemThuongXuyen=?, DiemGiuaKy=?, " +
                     "DiemCuoiKy=?, DiemTongKet=? WHERE DiemID=? AND MaSV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getMaMH());
            ps.setString(2, d.getHocKy());
            ps.setObject(3, d.getDiemThuongXuyen());
            ps.setObject(4, d.getDiemGiuaKy());
            ps.setObject(5, d.getDiemCuoiKy());
            ps.setObject(6, d.getDiemTongKet());
            ps.setInt(7, d.getDiemID());
            ps.setString(8, d.getMaSV());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa điểm theo ID và sinh viên
    public boolean deleteByIdAndMaSV(int diemID, String maSV) {
        String sql = "DELETE FROM DiemCaNhan WHERE DiemID=? AND MaSV=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, diemID);
            ps.setString(2, maSV);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tính GPA dựa trên danh sách điểm
    public double tinhGPA(List<DiemCaNhan> ds) {
        double tongTC = 0;
        double tongDiem = 0;

        for (DiemCaNhan d : ds) {
            if (d.getDiemTongKet() != null) {
                tongTC += d.getSoTinChi();
                tongDiem += d.getDiemTongKet() * d.getSoTinChi();
            }
        }

        return (tongTC == 0) ? 0 : tongDiem / tongTC;
    }

    // Chuyển ResultSet thành đối tượng DiemCaNhan
    private DiemCaNhan mapRow(ResultSet rs) throws SQLException {
        DiemCaNhan d = new DiemCaNhan();
        d.setDiemID(rs.getInt("DiemID"));
        d.setMaSV(rs.getString("MaSV"));
        d.setHoTen(rs.getString("HoTen"));
        d.setMaMH(rs.getString("MaMH"));
        d.setTenMonHoc(rs.getString("TenMonHoc"));
        d.setSoTinChi(rs.getInt("SoTinChi"));
        d.setHocKy(rs.getString("HocKy"));

        d.setDiemThuongXuyen(rs.getObject("DiemThuongXuyen") != null ? rs.getDouble("DiemThuongXuyen") : null);
        d.setDiemGiuaKy(rs.getObject("DiemGiuaKy") != null ? rs.getDouble("DiemGiuaKy") : null);
        d.setDiemCuoiKy(rs.getObject("DiemCuoiKy") != null ? rs.getDouble("DiemCuoiKy") : null);
        d.setDiemTongKet(rs.getObject("DiemTongKet") != null ? rs.getDouble("DiemTongKet") : null);

        return d;
    }
}
