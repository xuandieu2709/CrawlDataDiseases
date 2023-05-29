/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package crawldatadiseases;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Diseases;
import model.TagA;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Dell
 */
public class CrawlDataDiseases {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        List<TagA> listdata = getListTagA();
        List<Diseases> listDiseases = getListDataDisease(listdata);
        convertSaveFileJson(listDiseases);
    }
     public static List<TagA> getListTagA() throws IOException
    {
        List<TagA> listdata = new ArrayList<>();
        Document doc = Jsoup.connect("https://www.vinmec.com/vi/benh/").timeout(5000).get();
        doc.outputSettings().charset("UTF-8");
        Elements links = doc.select("section.disease-body.collapsible-container.collapsible-block.collapsed.screen-sm >ul >li");
        int count = 0;
        for (Element link : links) {
            System.out.println("hef " + count + link.select("a").attr("href"));
            System.out.println("text " + count + link.select("a").text());
            TagA taga = new TagA();
            taga.setTaghref(link.select("a").attr("href"));
            taga.setTagname(link.select("a").text());
            listdata.add(taga);
            count++;
        }
        return listdata;
    }
    public static List<Diseases> getListDataDisease(List<TagA> listdata) throws IOException
    {
        List<Diseases> listDiseases = new ArrayList<>();
    //         Duyệt qua các bệnh và lấy chi tiết
        for (TagA taga : listdata) {
            Document document = Jsoup.connect("https://www.vinmec.com" + taga.getTaghref()).timeout(5000).get();
            document.outputSettings().charset("UTF-8");
            Elements linkss = document.select("section.collapsible-container.collapsible-block.collapsed.screen-sm");
            Diseases diseases = new Diseases();
            diseases.setId(extractNumber(taga.getTaghref()));
            diseases.setName(taga.getTagname());
            List<String> url = new ArrayList<>();
            Element getImg = linkss.first();
            Elements tagdivImage = getImg.select("a");
            for (Element element : tagdivImage) {
                String cutURL = extractURL(element.attr("style"));
                if (cutURL != null) {
                    url.add(cutURL);
                }
            }
            for (Element link : linkss) {
                String text = link.select(":root > :not(h2.header.collapse-trigger)").text();
                Elements tagH2 = link.select("h2.header.collapse-trigger");
                setAtributeForDiseases(checkText(tagH2.text()), text, diseases);
            }
            diseases.setImage(url);
            System.out.println(diseases.getName() + "\n"
                    + diseases.getOverview() + "\n"
                    + diseases.getPrevent() + "\n"
                    + diseases.getReason() + "\n"
                    + diseases.getSubjects_at_risk() + "\n"
                    + diseases.getSymptom() + "\n"
                    + diseases.getTransmission_route() + "\n"
                    + diseases.getDiagnostic_measures() + "\n"
                    + diseases.getTreatment_measures() + "\n");
            System.out.println("-----");
            listDiseases.add(diseases);
        }
        return listDiseases;
    }

    public static String extractURL(String input) {
        int startIndex = input.indexOf("url(") + 4;
        int endIndex = input.lastIndexOf(")");

        if (startIndex != -1 && endIndex != -1) {
            return input.substring(startIndex, endIndex);
        }

        return null; // Nếu không tìm thấy URL
    }

    private static void funcMain() throws IOException {
        List<TagA> listdata = new ArrayList<>();
        List<Diseases> listDiseases = new ArrayList<>();
        Document doc = Jsoup.connect("https://www.vinmec.com/vi/benh/").get();
        doc.outputSettings().charset("UTF-8");
        Elements links = doc.select("section.disease-body.collapsible-container.collapsible-block.collapsed.screen-sm >ul >li");
        int count = 0;
        for (Element link : links) {
            System.out.println("hef " + count + link.select("a").attr("href"));
            System.out.println("text " + count + link.select("a").text());
            TagA taga = new TagA();
            taga.setTaghref(link.select("a").attr("href"));
            taga.setTagname(link.select("a").text());
            listdata.add(taga);
            count++;
        }
        for (TagA taga : listdata) {
            Document document = Jsoup.connect("https://www.vinmec.com" + taga.getTaghref()).timeout(5000).get();
            doc.outputSettings().charset("UTF-8");
            Elements linkss = document.select("section.collapsible-container.collapsible-block.collapsed.screen-sm");
            Diseases diseases = new Diseases();
            diseases.setId(extractNumber(taga.getTaghref()));
            diseases.setName(taga.getTagname());
            int newcount = 0;
            for (Element link : linkss) {
                Elements tagH2 = link.select("h2.header.collapse-trigger > span");
                setAtributeForDiseases(checkText(tagH2.text()), link.text(), diseases);
                List<String> url = new ArrayList<>();
                if (newcount == 0) {
                    Elements tagdivImage = link.select("a");
                    for (Element element : tagdivImage) {
                        System.out.println(element.attr("style"));
                    }
                    diseases.setImage(url);
                }
                count++;
            }
            System.out.println("-----");

            listDiseases.add(diseases);
        }
    }

    private static List<Callable<Diseases>> createTasks(List<TagA> listdata) {
        // Tạo danh sách các Callable task để thực hiện công việc trên từng phần tử của listdata
        List<Callable<Diseases>> tasks = new ArrayList<>();
        for (TagA taga : listdata) {
            Callable<Diseases> task = () -> {
                Document document = Jsoup.connect("https://www.vinmec.com" + taga.getTaghref()).timeout(5000).get();
                document.outputSettings().charset("UTF-8");
                Elements linkss = document.select("section.collapsible-container.collapsible-block.collapsed.screen-sm");
                Diseases diseases = new Diseases();
                for (Element link : linkss) {
                    Elements tagH2 = link.select("h2.header.collapse-trigger > span");
                    setAtributeForDiseases(checkText(tagH2.text()), link.text(), diseases);
                }
                return diseases;
            };
            tasks.add(task);
        }
        return tasks;
    }

    public static int checkText(String str) {
        str = str.toLowerCase();
        if (str.contains("tổng quan")) {
            return 1;
        }
        if (str.contains("nguyên nhân")) {
            return 2;
        }
        if (str.contains("triệu chứng")) {
            return 3;
        }
        if (str.contains("đường lây truyền")) {
            return 4;
        }
        if (str.contains("đối tượng nguy cơ")) {
            return 5;
        }
        if (str.contains("phòng ngừa")) {
            return 6;
        }
        if (str.contains("biện pháp chẩn đoán")) {
            return 7;
        }
        if (str.contains("biện pháp điều trị")) {
            return 8;
        }
        return 0;
    }

    public static void setAtributeForDiseases(int number, String str, Diseases diseases) {
        switch (number) {
            case 1:
                diseases.setOverview(str);
                break;
            case 2:
                diseases.setReason(str);
                break;
            case 3:
                diseases.setSymptom(str);
                break;
            case 4:
                diseases.setTransmission_route(str);
                break;
            case 5:
                diseases.setSubjects_at_risk(str);
                break;
            case 6:
                diseases.setPrevent(str);
                break;
            case 7:
                diseases.setDiagnostic_measures(str);
                break;
            case 8:
                diseases.setTreatment_measures(removeSeemore(str));
                break;
            default:
                break;
        }
    }

    private static void convertSaveFileJson(List<Diseases> list) {
        // Khởi tạo danh sách
        // Khởi tạo đối tượng Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            // Chuyển đổi danh sách thành chuỗi JSON
            String json = gson.toJson(list);
            // Lưu chuỗi JSON vào tập tin
            FileWriter writer = new FileWriter("src/file/listbenhtatnice.json");
            writer.write(json);
            writer.close();
            System.out.println("Đã lưu danh sách vào tập tin listbenhtatnew.json: " + "SUM RECORD = " + list.size());
        } catch (IOException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
    
    private static int extractNumber(String str) {
        Pattern pattern = Pattern.compile("(\\d+)[^\\d]*$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String numberStr = matcher.group(1);
            return Integer.parseInt(numberStr);
        }
        return 0;
    }

    public static String extractTextFromUrl(String url) {
        String text = "";
        int startIndex = url.indexOf("(");
        int endIndex = url.indexOf(")");
        text = url.substring(startIndex + 1, endIndex);
        return text;
    }
    public static String removeSeemore(String text)
    {
        int startIndex = text.indexOf("Xem thêm:");
        if (startIndex != -1) {
            String modifiedText = text.substring(0, startIndex);
            return modifiedText;
        } else {
            System.out.println("Chuỗi 'Xem thêm:' không tồn tại trong văn bản.");
        }
        return "";
    }
}
