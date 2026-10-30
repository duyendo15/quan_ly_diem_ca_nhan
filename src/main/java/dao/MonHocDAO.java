package dao;

import model.MonHoc;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonHocDAO {

    // Lấy danh sách môn học của một sinh viên
    public List<MonHoc> findByMaSV(String maSV) {
        List<MonHoc> ds = new ArrayList<>();
        String sql = "SELECT * FROM MonHoc WHERE MaSV=? ORDER BY MaMH";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSV);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MonHoc m = new MonHoc();
                m.setMaMH(rs.getString("MaMH"));
                m.setMaSV(rs.getString("MaSV"));
                m.setTenMonHoc(rs.getString("TenMonHoc"));
                m.setSoTinChi(rs.getInt("SoTinChi"));
                ds.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }

    // Thêm môn học
    public boolean insert(MonHoc m) {
        String sql = "INSERT INTO MonHoc(MaMH, MaSV, TenMonHoc, SoTinChi) VALUES(?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getMaMH());
            ps.setString(2, m.getMaSV());
            ps.setString(3, m.getTenMonHoc());
            ps.setInt(4, m.getSoTinChi());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Lấy một môn học theo mã môn và mã sinh viên
    public MonHoc findById(String maMH, String maSV) {
        String sql = "SELECT * FROM MonHoc WHERE MaMH=? AND MaSV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maMH);
            ps.setString(2, maSV);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                MonHoc m = new MonHoc();
                m.setMaMH(rs.getString("MaMH"));
                m.setMaSV(rs.getString("MaSV"));
                m.setTenMonHoc(rs.getString("TenMonHoc"));
                m.setSoTinChi(rs.getInt("SoTinChi"));
                return m;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Cập nhật môn học
    public boolean update(MonHoc m) {
        String sql = "UPDATE MonHoc SET TenMonHoc=?, SoTinChi=? WHERE MaMH=? AND MaSV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getTenMonHoc());
            ps.setInt(2, m.getSoTinChi());
            ps.setString(3, m.getMaMH());
            ps.setString(4, m.getMaSV());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Xóa môn học
    public boolean delete(String maMH, String maSV) {
        String sql = "DELETE FROM MonHoc WHERE MaMH=? AND MaSV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maMH);
            ps.setString(2, maSV);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
