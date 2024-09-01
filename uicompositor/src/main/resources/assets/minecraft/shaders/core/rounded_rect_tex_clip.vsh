#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 vertexColor;
out vec2 vertexUV;
out vec2 texCoord0;
out vec2 RawPosition;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    RawPosition = ((ProjMat * ModelViewMat * vec4(Position, 1.0)).xy + 1) / 2;
    vertexColor = Color;
    vertexUV = Position.xy;
    texCoord0 = UV0;
}
