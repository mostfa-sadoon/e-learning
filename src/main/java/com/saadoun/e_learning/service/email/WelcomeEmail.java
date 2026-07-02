package com.saadoun.e_learning.service.email;

import com.saadoun.e_learning.model.Student;

public class WelcomeEmail implements AbstractEmail{

    private final Student student;

    public WelcomeEmail(Student student) {
        this.student = student;
    }

    @Override
    public String getSubject() {
                return "Welcome to E-Learning, " + student.getName();
    }

    @Override
    public String getBody() {
                String studentName = escapeHtml(student.getName());

                return """
                                <!DOCTYPE html>
                                <html lang="en">
                                <head>
                                    <meta charset="UTF-8" />
                                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                                    <title>Welcome</title>
                                </head>
                                <body style="margin:0;padding:0;background:#fff8f1;font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;color:#1f2937;">
                                    <table role="presentation" width="100%%" cellspacing="0" cellpadding="0" style="background:#fff8f1;padding:24px 12px;">
                                        <tr>
                                            <td align="center">
                                                <table role="presentation" width="640" cellspacing="0" cellpadding="0" style="max-width:640px;width:100%%;background:#ffffff;border-radius:16px;overflow:hidden;border:1px solid #fed7aa;">
                                                    <tr>
                                                        <td style="padding:28px 32px;background:linear-gradient(135deg,#f97316,#ef4444);color:#ffffff;">
                                                            <p style="margin:0;font-size:12px;letter-spacing:1.2px;text-transform:uppercase;opacity:0.9;">E-Learning Platform</p>
                                                            <h1 style="margin:10px 0 0;font-size:28px;line-height:1.2;">Welcome aboard</h1>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding:30px 32px;">
                                                            <p style="margin:0 0 14px;font-size:16px;line-height:1.6;">Hi <strong>%s</strong>,</p>
                                                            <p style="margin:0 0 14px;font-size:16px;line-height:1.6;">Your account is ready, and your learning journey starts now.</p>
                                                            <p style="margin:0 0 20px;font-size:15px;line-height:1.6;color:#374151;">Explore courses, enroll in topics you love, and build your skills step by step.</p>

                                                            <div style="border:1px dashed #fdba74;background:#fff7ed;border-radius:12px;padding:16px 18px;">
                                                                <p style="margin:0;font-size:14px;line-height:1.6;color:#9a3412;">Tip: complete your profile and enroll in your first course to get the best personalized recommendations.</p>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding:18px 32px;background:#fffbeb;border-top:1px solid #fde68a;">
                                                            <p style="margin:0;font-size:12px;line-height:1.5;color:#92400e;">Thank you for joining E-Learning Platform.</p>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </body>
                                </html>
                                """.formatted(studentName);
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
