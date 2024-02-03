package com.tteokguk.tteokguk.tteokguk.constants;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public enum BackgroundColor {
    GREEN,
    YELLOW,
    PINK,
    BLUE;

    public static List<BackgroundColor> toBackgroundColor(List<String> inputs) {
        return inputs.stream()
                .map(BackgroundColor::valueOf)
                .toList();
    }

    public static BackgroundColor random() {
        Random random = new Random();

        BackgroundColor[] backgroundColor = BackgroundColor.values();
        int randomIdx = random.nextInt(backgroundColor.length);
        return backgroundColor[randomIdx];
    }
}
