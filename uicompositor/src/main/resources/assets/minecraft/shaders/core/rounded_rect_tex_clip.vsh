#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec2 Resolution;

out vec4 vertexColor;
out vec2 vertexUV;
out vec2 texCoord0;
out vec2 texCoord;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexColor = Color;
    vertexUV = Position.xy;
    texCoord0 = UV0;

    texCoord = Position.xy / Resolution;
}
