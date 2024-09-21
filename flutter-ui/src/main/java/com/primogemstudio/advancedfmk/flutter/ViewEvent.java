package com.primogemstudio.advancedfmk.flutter;

import static com.primogemstudio.advancedfmk.flutter.Events.instances;

public class ViewEvent {
    public static void resize(int width, int height) {
        instances.forEach(i -> {
            i.rect.left = (width - i.width) / 2;
            i.rect.right = (width + i.width) / 2;
            i.rect.top = (height - i.height) / 2;
            i.rect.bottom = (height + i.height) / 2;
            i.sendSizeEvent();
        });
    }
}
