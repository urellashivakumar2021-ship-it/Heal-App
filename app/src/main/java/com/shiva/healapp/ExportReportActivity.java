package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.OutputStream;

public class ExportReportActivity extends AppCompatActivity {

    Button btnTxt, btnPdf;
    DBHelper db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_report);

        btnTxt = findViewById(R.id.btnExportTxt);
        btnPdf = findViewById(R.id.btnExportPdf);

        db = new DBHelper(this);

        SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = sp.getInt("user_id", -1);

        btnTxt.setOnClickListener(v -> exportTxt());
        btnPdf.setOnClickListener(v -> exportPdf());
    }

    // ================= TXT EXPORT =====================
    private void exportTxt() {
        StringBuilder data = new StringBuilder();
        data.append("HEAL - Health Report\n");
        data.append("---------------------------\n\n");

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT * FROM health_logs WHERE user_id=? ORDER BY timestamp DESC",
                new String[]{String.valueOf(userId)}
        );

        while (c.moveToNext()) {
            data.append("Date: ").append(c.getString(c.getColumnIndexOrThrow("timestamp"))).append("\n");
            data.append("Sleep: ").append(c.getInt(c.getColumnIndexOrThrow("sleep"))).append(" hrs\n");
            data.append("Water: ").append(c.getDouble(c.getColumnIndexOrThrow("water"))).append(" L\n");
            data.append("Stress: ").append(c.getInt(c.getColumnIndexOrThrow("stress"))).append("\n");
            data.append("Diet: ").append(c.getString(c.getColumnIndexOrThrow("diet"))).append("\n");
            data.append("Activity: ").append(c.getString(c.getColumnIndexOrThrow("activity"))).append("\n");
            data.append("Mood: ").append(c.getString(c.getColumnIndexOrThrow("mood"))).append("\n");
            data.append("Heart Rate: ").append(c.getInt(c.getColumnIndexOrThrow("heartrate"))).append("\n");
            data.append("---------------------------\n\n");
        }

        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Downloads.DISPLAY_NAME, "HEAL_Report.txt");
            values.put(MediaStore.Downloads.MIME_TYPE, "text/plain");

            Uri uri = getContentResolver()
                    .insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

            OutputStream os = getContentResolver().openOutputStream(uri);
            os.write(data.toString().getBytes());
            os.close();

            Toast.makeText(this, "TXT saved in Downloads", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "TXT export failed", Toast.LENGTH_SHORT).show();
        }
    }

    // ================= PDF EXPORT =====================
    private void exportPdf() {

        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Downloads.DISPLAY_NAME, "HEAL_Report.pdf");
            values.put(MediaStore.Downloads.MIME_TYPE, "application/pdf");

            Uri uri = getContentResolver()
                    .insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

            OutputStream os = getContentResolver().openOutputStream(uri);

            PdfWriter writer = new PdfWriter(os);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("HEAL - Health Report"));
            document.add(new Paragraph("---------------------------\n\n"));

            Cursor c = db.getReadableDatabase().rawQuery(
                    "SELECT * FROM health_logs WHERE user_id=? ORDER BY timestamp DESC",
                    new String[]{String.valueOf(userId)}
            );

            while (c.moveToNext()) {
                document.add(new Paragraph("Date: " + c.getString(c.getColumnIndexOrThrow("timestamp"))));
                document.add(new Paragraph("Sleep: " + c.getInt(c.getColumnIndexOrThrow("sleep")) + " hrs"));
                document.add(new Paragraph("Water: " + c.getDouble(c.getColumnIndexOrThrow("water")) + " L"));
                document.add(new Paragraph("Stress: " + c.getInt(c.getColumnIndexOrThrow("stress"))));
                document.add(new Paragraph("Diet: " + c.getString(c.getColumnIndexOrThrow("diet"))));
                document.add(new Paragraph("Activity: " + c.getString(c.getColumnIndexOrThrow("activity"))));
                document.add(new Paragraph("Mood: " + c.getString(c.getColumnIndexOrThrow("mood"))));
                document.add(new Paragraph("Heart Rate: " + c.getInt(c.getColumnIndexOrThrow("heartrate"))));
                document.add(new Paragraph("---------------------------\n"));
            }

            document.close();

            Toast.makeText(this, "PDF saved in Downloads", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "PDF export failed", Toast.LENGTH_SHORT).show();
        }
    }
}
