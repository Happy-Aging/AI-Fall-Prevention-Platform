package happyaging.server.service.product;

import happyaging.server.domain.image.Location;
import happyaging.server.domain.product.Product;
import happyaging.server.domain.product.Recommend;
import happyaging.server.domain.senior.Senior;
import happyaging.server.repository.product.RecommendRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendRepository recommendRepository;

    @Transactional
    public void recommendAllProduct(Senior senior, List<Product> products) {
        List<Recommend> recommends = new ArrayList<>();
        for (Product product : products) {
            Long productId = product.getId();
            if (productId < 7) {
                recommends.add(Recommend.create(senior, product, Location.BATHROOM));
            } else if (productId < 12) {
                recommends.add(Recommend.create(senior, product, Location.ROOM));
            } else if (productId < 14) {
                recommends.add(Recommend.create(senior, product, Location.ENTRANCE));
            }

            if (productId == 14 || productId == 8) {
                recommends.add(Recommend.create(senior, product, Location.KITCHEN));
            } else if (productId == 15) {
                recommends.add(Recommend.create(senior, product, Location.BATHROOM));
            } else if (productId == 16 || productId == 1) {
                recommends.add(Recommend.create(senior, product, Location.ENTRANCE));
            } else if (productId == 2) {
                recommends.add(Recommend.create(senior, product, Location.ROOM));
            }
        }
        recommendRepository.saveAll(recommends);
    }

    @Transactional(readOnly = true)
    public List<Recommend> findALLBySeniorAndLocation(Senior senior, Location location) {
        return recommendRepository.findAllBySeniorIdAndLocation(senior.getId(), location);
    }
}
