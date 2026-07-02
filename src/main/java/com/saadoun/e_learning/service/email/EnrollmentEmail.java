package com.saadoun.e_learning.service.email;

import com.saadoun.e_learning.model.Course;
import com.saadoun.e_learning.model.Student;

public class EnrollmentEmail implements AbstractEmail{

    private final Student student;
    private final Course course;

    public EnrollmentEmail(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    @Override
    public String getSubject() {
                return "You're enrolled: " + course.getTitle();
    }

    @Override
    public String getBody() {
                String studentName = escapeHtml(student.getName());
                String courseTitle = escapeHtml(course.getTitle());

                return """
                                <!DOCTYPE html>
                                <html lang="en">
                                <head>
                                    <meta charset="UTF-8" />
                                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                                    <title>Enrollment Confirmation</title>
                                </head>
                                <body style="margin:0;padding:0;background:#f4f7fb;font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;color:#1f2937;">
                                    <table role="presentation" width="100%%" cellspacing="0" cellpadding="0" style="background:#f4f7fb;padding:24px 12px;">
                                        <tr>
                                            <td align="center">
                                                <table role="presentation" width="640" cellspacing="0" cellpadding="0" style="max-width:640px;width:100%%;background:#ffffff;border-radius:16px;overflow:hidden;border:1px solid #e5e7eb;">
                                                    <tr>
                                                        <td style="padding:28px 32px;background:linear-gradient(135deg,#0f766e,#0ea5e9);color:#ffffff;">
                                                            <p style="margin:0;font-size:12px;letter-spacing:1.2px;text-transform:uppercase;opacity:0.9;">E-Learning Platform</p>
                                                            <h1 style="margin:10px 0 0;font-size:28px;line-height:1.2;">Enrollment confirmed</h1>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding:30px 32px;">
                                                            <p style="margin:0 0 14px;font-size:16px;line-height:1.6;">Hello <strong>%s</strong>,</p>
                                                            <p style="margin:0 0 20px;font-size:16px;line-height:1.6;">Great news. Your enrollment has been completed successfully.</p>

                                                            <div style="border:1px solid #dbeafe;background:#eff6ff;border-radius:12px;padding:18px 20px;margin:0 0 20px;">
                                                                <p style="margin:0 0 8px;font-size:13px;color:#475569;text-transform:uppercase;letter-spacing:0.8px;">Course</p>
                                                                <p style="margin:0;font-size:20px;font-weight:700;color:#0f172a;">%s</p>
                                                            </div>

                                                            <p style="margin:0 0 12px;font-size:15px;line-height:1.6;color:#374151;">You can now start learning at your own pace and track your progress directly from your dashboard.</p>
                                                            <p style="margin:0;font-size:15px;line-height:1.6;color:#374151;">We are excited to have you with us.</p>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding:18px 32px;background:#f8fafc;border-top:1px solid #e5e7eb;">
                                                            <p style="margin:0;font-size:12px;line-height:1.5;color:#64748b;">This is an automated message from E-Learning Platform.</p>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </body>
                                </html>
                                """.formatted(studentName, courseTitle);
    }

        private String escapeHtml(String value) {
                if (value == null) {
                        return "";
                }
                return value
                                .replace("&", "&amp;")
                                .replace("<", "&lt;")
                                .replace(">", "&gt;")
                                .replace("\"", "&quot;")
                                .replace("'", "&#39;");
        }
}
