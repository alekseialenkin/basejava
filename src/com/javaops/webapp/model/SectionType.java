package com.javaops.webapp.model;

import com.javaops.webapp.util.DateUtil;

import java.util.List;

public enum SectionType {
    PERSONAL("Личные качества") {
        @Override
        public String toHtml(AbstractSection value) {
            return (value == null) ? "" : toHtml0(((TextSection) value).getText());
        }

        @Override
        public String toHtmlEdit(AbstractSection value, SectionType key) {
            return "<dl>" + "<dt>" + key.getTitle() + "</dt>" + "<dd><input type='text' name='" + key + "'" + "size=30 value='" + ((TextSection) value).getText() + "'></dd></dl>";
        }
    },
    OBJECTIVE("Позиция") {
        @Override
        public String toHtml(AbstractSection value) {
            return (value == null) ? "" : toHtml0(((TextSection) value).getText());
        }

        @Override
        public String toHtmlEdit(AbstractSection value, SectionType key) {
            return "<dl>" + "<dt>" + key.getTitle() + "</dt>" + "<dd><input type='text' name='" + key + "'" + "size=30 value='" + ((TextSection) value).getText() + "'>" + "</dd></dl>";
        }
    },
    ACHIEVEMENT("Достижения") {
        @Override
        public String toHtml(AbstractSection value) {
            return (value == null) ? "" : toHtml0(((ListSection) value).getStrings());
        }

        @Override
        public String toHtmlEdit(AbstractSection value, SectionType key) {
            return "<dl>" + "<dt>" + key.getTitle() + "</dt>" + "<textarea name='" + key + "'>" + String.join("\n", ((ListSection) value).getStrings()) + "</textarea>" + "</dl>";
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtml(AbstractSection value) {
            return (value == null) ? "" : toHtml0(((ListSection) value).getStrings());
        }

        @Override
        public String toHtmlEdit(AbstractSection value, SectionType key) {
            return "<dl>" + "<dt>" + key.getTitle() + "</dt>" + "<textarea name='" + key + "'>" + String.join("\n", ((ListSection) value).getStrings()) + "</textarea>" + "</dl>";
        }
    },
    EXPERIENCE("Опыт работы") {
        @Override
        public String toHtml(AbstractSection value) {
            return (value == null) ? "" : toHtml0(((CompanySection) value).getCompanies());
        }

        @Override
        public <T> String toHtml0(List<T> companies) {
            StringBuilder st = new StringBuilder();
            st.append("<h2>").append(getTitle()).append("</h2");
            for (T c : companies) {
                createTable((Company) c, st);
            }
            return st.toString();
        }

        @Override
        public String toHtmlEdit(AbstractSection value, SectionType key) {
            return editCategories(value, key);
        }
    },
    EDUCATION("Образование") {
        @Override
        public String toHtml(AbstractSection value) {
            return (value == null) ? "" : toHtml0(((CompanySection) value).getCompanies());
        }

        @Override
        public <T> String toHtml0(List<T> companies) {
            StringBuilder st = new StringBuilder();
            st.append("<h2>").append(getTitle()).append("</h2");
            for (T c : companies) {
                createTable((Company) c, st);
            }
            return st.toString();
        }

        @Override
        public String toHtmlEdit(AbstractSection value, SectionType key) {
            return editCategories(value, key);
        }
    };
    private final String title;
    private static final long serialVersionUID = 1L;

    SectionType(String title) {
        this.title = title;
    }

    protected String toHtml0(String value) {
        return "<h2>" + getTitle() + "</h2>" + "<p>" + value + "</p>";
    }

    protected <T> String toHtml0(List<T> strings) {
        StringBuilder st = new StringBuilder();
        st.append("<h2>").append(getTitle()).append("</h2>").append("<ul>");
        for (T s : strings) {
            st.append("<li>").append(s).append("</li>");
        }
        st.append("</ul>");
        return st.toString();
    }

    public String toHtml(AbstractSection value) {
        return (value == null) ? "" : toHtml0(value.toString());
    }

    protected void createTable(Company c, StringBuilder st) {
        st.append("<section>");
        st.append("<table>").append("<caption>").append("<a href='").append(c.getWebsite().getUrl()).append("'>").append(c.getWebsite().getName()).append("</a>").append("</caption>");
        for (Company.Period p : c.getPeriods()) {
            if (p.getTitle().trim().length() != 0) {
                if (p.getBegin().getYear() == 0) {
                    st.append("<tr>").append("<th>").append("</th>")
                            .append("<th>").append(p.getTitle()).append("</th>").append("</tr>");
                    st.append("<tr>").append("<td></td>").append("<td>").append(p.getDescription()).append("</td>").append("</tr>");
                } else if (p.getEnd().equals(DateUtil.NOW)) {
                    st.append("<tr>").append("<th>").append(p.getBegin().getMonthValue()).append("/").append(p.getBegin().getYear())
                            .append("-").append("Сейчас").append("</th>")
                            .append("<th>").append(p.getTitle()).append("</th>").append("</tr>");
                    st.append("<tr>").append("<td></td>").append("<td>").append(p.getDescription()).append("</td>").append("</tr>");
                } else {
                    st.append("<tr>").append("<th>").append(p.getBegin().getMonthValue()).append("/").append(p.getBegin().getYear())
                            .append("-").append(p.getEnd().getMonthValue()).append("/").append(p.getEnd().getYear()).append("</th>")
                            .append("<th>").append(p.getTitle()).append("</th>").append("</tr>");
                    st.append("<tr>").append("<td></td>").append("<td>").append(p.getDescription()).append("</td>").append("</tr>");
                }
            }
        }
        st.append("</table>").append("</section>");
    }

    public String toHtmlEdit(AbstractSection value, SectionType key) {
        return "<dl><dt>" + key.name() + "</dt><dd><input type='text' name='" + key + "'size=30 value=''" + "></dd>" + "</dt>";
    }

    public String editCategories(AbstractSection value, SectionType key) {
        int i = 0;
        StringBuilder st = new StringBuilder();
        st.append("<p><h4>").append(key.getTitle()).append("</h4></p>");
        for (Company c : ((CompanySection) value).getCompanies()) {
            st.append("<dl>" + "<dt>" + "URL" + "</dt>" + "<dd><input type='text' name='").append(key).append("url'").append("size=30 value='").append(c.getWebsite().getUrl()).append("'></dd>");
            st.append("<dt>" + "Название" + "</dt>" + "<dd><input type='text' name='").append(key).append("'").append("size=30 value='").append(c.getWebsite().getName()).append("'></dd>");
            for (Company.Period p : c.getPeriods()) {
                if (p.getBegin().getYear() == 0 || p.getEnd().equals(DateUtil.NOW)){
                    st.append("<p>" + "<dt>" + "Дата начала" + "<dd><input type='text' name='").append(key).append(i).append("begin'").append("size=30 value='' placeholder='ММ/ГГГГ'></dd>").append("</dt>");
                    st.append("<dt>" + "Дата окончания" + "<dd><input type='text' name='").append(key).append(i).append("end'").append("size=30 value=''placeholder='ММ/ГГГГ'></dd>").append("</dt>").append("</p>");
                }else {
                    st.append("<p>" + "<dt>" + "Дата начала" + "<dd><input type='text' name='").append(key).append(i).append("begin'").append("size=30 value='").append(p.getBegin().getMonthValue()).append("/").append(p.getBegin().getYear()).append("' placeholder='ММ/ГГГГ'></dd>").append("</dt>");
                    st.append("<dt>" + "Дата окончания" + "<dd><input type='text' name='").append(key).append(i).append("end'").append("size=30 value='").append(p.getEnd().getMonthValue()).append("/").append(p.getEnd().getYear()).append("'placeholder='ММ/ГГГГ'></dd>").append("</dt>").append("</p>");
                }
                    st.append("<p>" + "<dt>" + "Заголовок" + "<dd><input type='text' name='").append(key).append(i).append("title'").append("size=30 value='").append(p.getTitle()).append("'></dd>").append("</dt>");
                    st.append("<dt>" + "Описание" + "<dd><input type='text' name='").append(key).append(i).append("description'").append("size=30 value='").append(p.getDescription()).append("'></dd>").append("</dt>").append("</p>");

            }
            st.append("</dl>");
            i++;
        }
        return st.toString();
    }


    public String getTitle() {
        return title;
    }
}
