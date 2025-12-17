//package ee.taavi.veebipood.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CronService {
//
//    // * sekundid
//    // ** minutid
//    // ** tunnid (max 24)
//    // *** kuupäev kuus (max 31)
//    // **** kuu (max 12)
//    // ***** nädalapäev (0-7), pühapäev on nii 0 kui ka 7
//    @Scheduled(cron = "*/30 * * * * *")
//    public void startEverySecond(){
//        System.out.println("Starting every 30 seconds");
//    }
//
////    @Autowired
////    private BronRepository bronRepository;
//
//    @Scheduled(cron = "0 0 9-17 * * 1-5")
//    public void startEveryHourOnWorkhours(){
//        // Custom päring kus on homne kuupäev
//        System.out.println("Saada email klientidele kellel on 24h pärast broneering");
//    }
//
//    @Scheduled(cron = "0 0 13 22 * *")
//    public void startOnEvery22ndAt1300(){
//        // custom päring selle kuu arvete kohta, kellel on tasumata
//        System.out.println("Saada arve tasumise meeldetuletus");
//    }
//}
