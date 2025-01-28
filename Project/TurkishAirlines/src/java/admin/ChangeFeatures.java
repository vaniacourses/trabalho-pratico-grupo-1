package admin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import models.Features;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MuhammadHarris
 */
public class ChangeFeatures extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Features> f = (ArrayList<Features>) (getServletContext().getAttribute("features"));
        // Dentro do método doPost
        
        
        char[] s = {'e','b','f'};
        System.out.println(s);
        
        if (f == null || f.size() < 3) {
            System.out.println("Lista de Features não configurada ou tamanho insuficiente.");
            return;
        }
        
        
                
        
        System.out.println("Verificando lista de Features: " + f.size());
        if (f != null && f.size() >= 3) {
            System.out.println("Entrando no loop...");
            for (int i = 0; i < 3; i++) {
                System.out.println("Entrando na iteração " + i);
                //Saving old values
                (f.get(i)).setNewSeatPitch( f.get(i).getSeatPitch() );
                (f.get(i)).setNewSeatWidth( f.get(i).getSeatWidth() );            
                (f.get(i)).setNewVideoType( f.get(i).getVideoType() );            
                (f.get(i)).setNewPowerType( f.get(i).getPowerType() );            
                (f.get(i)).setNewSeatType( f.get(i).getSeatType() );            
                (f.get(i)).setNewPrice( f.get(i).getPrice() );

                // Pegando e imprimindo os parâmetros para cada feature
                String seatPitchParam = request.getParameter("seat_pitch_" + s[i]);
                System.out.println("seat_pitch_" + s[i] + ": " + seatPitchParam);
                if (seatPitchParam != null && !seatPitchParam.isEmpty()) {
                    (f.get(i)).setSeatPitch(Double.parseDouble(seatPitchParam));
                }

                String seatWidthParam = request.getParameter("seat_width_" + s[i]);
                System.out.println("seat_width_" + s[i] + ": " + seatWidthParam);
                if (seatWidthParam != null && !seatWidthParam.isEmpty()) {
                    (f.get(i)).setSeatWidth(Double.parseDouble(seatWidthParam));
                }

                String videoTypeParam = request.getParameter("video_" + s[i]);
                System.out.println("video_" + s[i] + ": " + videoTypeParam);
                if (videoTypeParam != null && !videoTypeParam.isEmpty()) {
                    (f.get(i)).setVideoType(videoTypeParam);
                }

                String powerTypeParam = request.getParameter("power_" + s[i]);
                System.out.println("power_" + s[i] + ": " + powerTypeParam);
                if (powerTypeParam != null && !powerTypeParam.isEmpty()) {
                    (f.get(i)).setPowerType(powerTypeParam);
                }

                String seatTypeParam = request.getParameter("seat_type_" + s[i]);
                System.out.println("seat_type_" + s[i] + ": " + seatTypeParam);
                if (seatTypeParam != null && !seatTypeParam.isEmpty()) {
                    (f.get(i)).setSeatType(seatTypeParam);
                }

                String priceParam = request.getParameter("price_" + s[i]);
                System.out.println("price_" + s[i] + ": " + priceParam);
                if (priceParam != null && !priceParam.isEmpty()) {
                    (f.get(i)).setPrice(Integer.parseInt(priceParam));
                }
            }

            // Verificando e configurando Wi-Fi para os tipos b e f
            String wifiB = request.getParameter("wifi_b");
            System.out.println("wifi_b: " + wifiB);
            if (wifiB != null && !wifiB.isEmpty()) {
                f.get(1).setWifi(wifiB);
            }

            String wifiF = request.getParameter("wifi_f");
            System.out.println("wifi_f: " + wifiF);
            if (wifiF != null && !wifiF.isEmpty()) {
                f.get(2).setWifi(wifiF);
            }

            // Configurando comida especial para o tipo f
            String specialFoodF = request.getParameter("special_food_f");
            System.out.println("special_food_f: " + specialFoodF);
            if (specialFoodF != null && !specialFoodF.isEmpty()) {
                f.get(2).setSpecialFood(specialFoodF);
            }   

            // Marcando que houve uma mudança nos valores
            Features.isChanged = true;

            // Redirecionando para a página de mudança de features
            response.sendRedirect("ChangeFeatures.jsp");     
        }
    }
}        