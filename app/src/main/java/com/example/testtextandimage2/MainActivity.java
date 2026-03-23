package com.example.testtextandimage2;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView tvTest;
    private final String mUrl1 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/e2af1492c6bd2f54e993b71bcbacdd42.webp";
    private final String mUrl2 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/646b678b6ac0e21d79d7d200c7f382d6.webp";
    private final String mUrl3 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/1a421e749f86e4d77e1553586b94c68a.webp";
    private final String mUrl4 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/e2af1492c6bd2f54e993b71bcbacdd42.webp";
    private final String mUrl5 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/646b678b6ac0e21d79d7d200c7f382d6.webp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvTest = findViewById(R.id.tv_test);
    }

    public void onTest1(View v) {
        // 示例组合： 图片1 图片2 图片3 名称 开着保时捷进入了房间
        String name = "名称";
        String tail = " 开着保时捷进入了房间";
        String[] urls = new String[]{mUrl1, mUrl2, mUrl3};
        loadAndShow(urls, name, tail);
    }

    public void onTest2(View v) {
        // 不同组合示例，带更多图片
        String name = "张三";
        String tail = " 开着保时捷进入了房间";
        String[] urls = new String[]{mUrl1, mUrl2, mUrl3, mUrl4, mUrl5};
        loadAndShow(urls, name, tail);
    }

    /**
     * 加载一组图片 URL，并在全部加载完成后将它们以 ImageSpan 的形式插入到 TextView 的文本中。
     * 图片将根据 TextView 的文字高度调整大小，保持与文字基线对齐。
     */
    private void loadAndShow(@NonNull String[] urls, @NonNull String name, @NonNull String tail) {
        // 构建基本文本模板：占位 + name + tail
        final SpannableStringBuilder ssb = new SpannableStringBuilder();

        int imgCount = urls.length;
        for (int i = 0; i < imgCount; i++) {
            ssb.append(" "); // 占位，用空格占位图片
        }
        ssb.append(name).append(tail);

        // 先设置文本，这样 TextView 的行宽测量能正确工作
        tvTest.setText(ssb);

        // 计算要把图片渲染成多大：使用 tvTest 的字体高度
        float textSizePx = tvTest.getTextSize(); // px
        int size = (int) (textSizePx * 3.1f); // 给一点额外的空间

        // 对每个 url 异步加载，然后把 drawable 插入对应位置
        for (int i = 0; i < imgCount; i++) {
            final int insertPos = i; // images are at start
            Glide.with(this)
                    .asDrawable()
                    .load(urls[i])
                    .dontAnimate()
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                            // 设置 bounds 以便 ImageSpan 正确占位
                            resource.setBounds(0, 0, size, size);
                            ImageSpan span = new ImageSpan(resource, ImageSpan.ALIGN_BOTTOM);

                            // 在我们自己的 SpannableStringBuilder 上设置 span，避免从 TextView 获取到不可变的 SpannedString
                            try {
                                ssb.setSpan(span, insertPos, insertPos + 1, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                                // 把更新后的 ssb 重新设置到 TextView，触发重绘
                                tvTest.setText(ssb);

                                // 如果 drawable 可动画，尝试启动
                                if (resource instanceof Animatable) {
                                    ((Animatable) resource).start();
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "Failed to set ImageSpan", e);
                            }

                        }

                        @Override
                        public void onLoadCleared(Drawable placeholder) {

                        }

                        @Override
                        public void onLoadFailed(Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            // 可扩展：显示一个占位图或移除该占位字符
                        }
                    });
        }
    }

}