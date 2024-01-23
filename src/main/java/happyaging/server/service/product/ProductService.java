package happyaging.server.service.product;

import happyaging.server.domain.product.Product;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.product.ProductRepository;
import happyaging.server.repository.product.RecommendRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RecommendRepository recommendRepository;

    @Transactional(readOnly = true)
    public Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_PRODUCT));
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }
}
