#version 150

in vec3 Position;

out vec2 texCoord;

uniform vec2 PositionOffset;

void main() {
    vec2 screenPos = Position.xy * 2.0 - 1.0 + PositionOffset;
    gl_Position = vec4(screenPos.x, screenPos.y, 1.0, 1.0);
    texCoord = vec2(Position.x, 1 - Position.y);
}
