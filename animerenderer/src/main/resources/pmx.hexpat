#pragma pattern_limit 0xFFFFF

u8 encode @ 0x9 [[hidden]];
u8 add_uv_num @ 0xA [[hidden]];
u8 vertex_index_size @ 0xB [[hidden]];
u8 texture_index_size @ 0xC [[hidden]];
u8 material_index_size @ 0xD [[hidden]];
u8 bone_index_size @ 0xE [[hidden]];
u8 morph_index_size @ 0xF [[hidden]];
u8 rigidbody_index_size @ 0x10 [[hidden]];

struct String {
    u32 length [[name("String Length")]];
    if (encode == 1) char data[length] [[name("String Content")]];
    else char16 data[length / 2] [[name("String Content")]];
};

struct Vec2 {
    float x;
    float y;
} [[static]];

struct Vec3 {
    float x;
    float y;
    float z;
} [[static]];

struct Vec4 {
    float x;
    float y;
    float z;
    float w;
} [[static]];

struct Quat {
    float a;
    float b; 
    float c;
    float d;
} [[static]];

struct PMXHeader {
    char magic[4] [[name("Magic"), comment("PMX Magic String: 'PMX '")]];
    float version [[name("Version")]];
    u8 data_size [[name("Data Size")]];
    u8 encode [[name("Encode"), comment("0: UTF-16, 1: UTF-8")]];
    u8 add_uv_num [[name("Addition UV Num")]];
    u8 vertex_index_size [[name("Vertex Index Size")]];
    u8 texture_index_size [[name("Texture Index Size")]];
    u8 material_index_size [[name("Material Index Size")]];
    u8 bone_index_size [[name("Bone Index Size")]];
    u8 morph_index_size [[name("Morph Index Size")]];
    u8 rigid_body_index_size [[name("Rigid Body Index Size")]];
} [[static]];

struct PMXInfo {
    String name [[name("Model Name")]];
    String englishname [[name("English Model Name")]];
    String comment [[name("Model Comment")]];
    String englishcomment [[name("English Model Comment")]];
} [[static]];

enum PMXVertexWeight : u8 {
    BDEF1 = 0, 
    BDEF2 = 1, 
    BDEF4 = 2, 
    SDEF = 3, 
    QDEF = 4
} [[static]];

struct PMXVertex {
    Vec3 pos [[name("Position")]];
    Vec3 normal [[name("Normal")]];
    Vec2 uv [[name("UV")]];
    Vec4 adduv[add_uv_num] [[name("Addition UV")]];
    PMXVertexWeight weight [[name("Weight type")]];
    
    if (weight == PMXVertexWeight::SDEF) {
        if (bone_index_size == 1) u8 bone_index[2] [[name("Bone Index")]];
        else if (bone_index_size == 2) u16 bone_index[2] [[name("Bone Index")]];
        else if (bone_index_size == 4) u32 bone_index[2] [[name("Bone Index")]];
        
        float bone_weight[1] [[name("Bone weight")]];
        
        Vec3 sdefC [[name("Param C")]];
        Vec3 sdefR0 [[name("Param R0")]];
        Vec3 sdefR1 [[name("Param R1")]];
    }
    else if (weight == PMXVertexWeight::BDEF4 || weight == PMXVertexWeight::QDEF) {
        if (bone_index_size == 1) u8 bone_index[4] [[name("Bone Index")]];
        else if (bone_index_size == 2) u16 bone_index[4] [[name("Bone Index")]];
        else if (bone_index_size == 4) u32 bone_index[4] [[name("Bone Index")]];
        
        float bone_weight[4] [[name("Bone weight")]];
    }
    else if (weight == PMXVertexWeight::BDEF2) {
        if (bone_index_size == 1) u8 bone_index[2] [[name("Bone Index")]];
        else if (bone_index_size == 2) u16 bone_index[2] [[name("Bone Index")]];
        else if (bone_index_size == 4) u32 bone_index[2] [[name("Bone Index")]];
        
        float bone_weight[1] [[name("Bone Weight")]];
    }
    else if (weight == PMXVertexWeight::BDEF1) {
        if (bone_index_size == 1) u8 bone_index[1] [[name("Bone Index")]];
        else if (bone_index_size == 2) u16 bone_index[1] [[name("Bone Index")]];
        else if (bone_index_size == 4) u32 bone_index[1] [[name("Bone Index")]];
    }
    
    float edge_mag [[name("Edge Margin")]];
};

struct PMXVertices {
    s32 num [[name("Vertex Count")]];
    PMXVertex vertices[num] [[name("Vertices")]];
} [[static]];

struct PMXFace8 {
    u8 Indices[3] [[name("Vertex Indices")]];
} [[static]];

struct PMXFace16 {
    u16 Indices[3] [[name("Vertex Indices")]];
} [[static]];

struct PMXFace32 {
    u32 Indices[3] [[name("Vertex Indices")]];
} [[static]];

struct PMXFaces {
    s32 num [[name("Face count")]];
    if (bone_index_size == 1) PMXFace8 faces[num / 3] [[name("Faces")]];
    else if (bone_index_size == 2) PMXFace16 faces[num / 3] [[name("Faces")]];
    else if (bone_index_size == 4) PMXFace32 faces[num / 3] [[name("Faces")]];
} [[static]];

struct PMXTextures {
    s32 num [[name("Texture count")]];
    String paths[num] [[name("Texture paths")]];
} [[static]];

enum PMXDrawModeFlags : u8 {
    BothFace = 0x01,
    GroundShadow = 0x02,
    CastSelfShadow = 0x04,
	RecieveSelfShadow = 0x08,
	DrawEdge = 0x10,
	VertexColor = 0x20,
	DrawPoint = 0x40,
	DrawLine = 0x80
};

enum PMXSphereMode : u8 {
    None = 0x00, 
    Mul = 0x01, 
    Add = 0x02, 
    SubTexture = 0x03
};

enum PMXToonMode : u8 {
    Separate, 
    Common
};

struct PMXMaterial {
    String name [[name("Material Name")]];
    String englishname [[name("Material English Name")]];
    
    Vec4 diffuse [[name("Diffuse")]];
    Vec3 specular [[name("Specular")]];
    float specular_power [[name("Specular Power")]];
    Vec3 ambient [[name("Ambient")]];
    
    PMXDrawModeFlags draw_mode [[name("Draw Mode")]];
    
    Vec4 edge_color [[name("Edge Color")]];
    float edge_size [[name("Edge Size")]];
    
    if (texture_index_size == 1) {
        s8 texture_index [[name("Texture Index")]];
        s8 sphere_texture_index [[name("Sphere Texture Index")]];
    }
    else if (texture_index_size == 2) {
        s16 texture_index [[name("Texture Index")]];
        s16 sphere_texture_index [[name("Sphere Texture Index")]];
    }
    else if (texture_index_size == 4) {
        s32 texture_index [[name("Texture Index")]];
        s32 sphere_texture_index [[name("Sphere Texture Index")]];
    }
    
    PMXSphereMode sphere_mode [[name("Sphere mode")]];
    PMXToonMode toon_mode [[name("Toon mode")]];
    
    if (toon_mode == PMXToonMode::Separate) {
        if (texture_index_size == 1) s8 toon_texture_index [[name("Toon Texture Index")]];
        else if (texture_index_size == 2) s16 toon_texture_index [[name("Toon Texture Index")]];
        else if (texture_index_size == 4) s32 toon_texture_index [[name("Toon Texture Index")]];
    }
    else if (toon_mode == PMXToonMode::Common) {
        s8 toon_texture_index [[name("Toon Texture Index")]];
    }
    
    String meno [[name("Meno")]];
    s32 num_faces_vertices [[name("Affected Faces")]];
};

struct PMXMaterials {
    s32 num [[name("Material Count")]];
    PMXMaterial materials[num] [[name("Materials")]];
} [[static]];

enum PMXBoneFlags : u16
{
	TargetShowMode = 0x0001,
	AllowRotate = 0x0002,
	AllowTranslate = 0x0004,
	Visible = 0x0008,
	AllowControl = 0x0010,
	IK = 0x0020,
	AppendLocal = 0x0080,
	AppendRotate = 0x0100,
	AppendTranslate = 0x0200,
	FixedAxis = 0x0400,
	LocalAxis = 0x0800,
	DeformAfterPhysics = 0x1000,
	DeformOuterParent = 0x2000,
};

struct PMXBoneIK {
    if (bone_index_size == 1) s8 ik_bone_index [[name("IK Bone Index")]];
    else if (bone_index_size == 2) s16 ik_bone_index [[name("IK Bone Index")]];
    else if (bone_index_size == 4) s32 ik_bone_index [[name("IK Bone Index")]];
    
    bool enable_limit [[name("Enable Limit")]];
    if (enable_limit) {
        Vec3 limit_min [[name("Limit Min")]];
        Vec3 limit_max [[name("Limit Max")]];
    }
};

struct PMXBoneIKs {
    if (bone_index_size == 1) s8 ik_target_bone_index [[name("IK target Bone Index")]];
    else if (bone_index_size == 2) s16 ik_target_bone_index [[name("IK target Bone Index")]];
    else if (bone_index_size == 4) s32 ik_target_bone_index [[name("IK target Bone Index")]];

    s32 ik_iteration_count [[name("IK Iteration Count")]];
    float ik_limit [[name("IK Limit")]];
    
    s32 link_count [[name("Link Count")]];
    PMXBoneIK links[link_count] [[name("Links")]];
};

struct PMXBone {
    String name [[name("Bone Name")]];
    String englishname [[name("Bone English Name")]];
    
    Vec3 pos [[name("Position")]];
    
    if (bone_index_size == 1) s8 parent_bone_index [[name("Parent Bone Index")]];
    else if (bone_index_size == 2) s16 parent_bone_index [[name("Parent Bone Index")]];
    else if (bone_index_size == 4) s32 parent_bone_index [[name("Parent Bone Index")]];
    
    s32 deform_depth [[name("Deform depth")]];
    u16 bone_flag [[name("Bone flag")]];
    
    if (bone_flag & 0x0001 == 0) {
        Vec3 position_offset [[name("Position offset")]];
    }
    else {
        if (bone_index_size == 1) s8 link_bone_index [[name("Link Bone Index")]];
        else if (bone_index_size == 2) s16 link_bone_index [[name("Link Bone Index")]];
        else if (bone_index_size == 4) s32 link_bone_index [[name("Link Bone Index")]];
    }
    if ((bone_flag & 0x0100 != 0) || (bone_flag & 0x0200 != 0)) {
        if (bone_index_size == 1) s8 append_bone_index [[name("Append Bone Index")]];
        else if (bone_index_size == 2) s16 append_bone_index [[name("Append Bone Index")]];
        else if (bone_index_size == 4) s32 append_bone_index [[name("Append Bone Index")]];
        
        float append_weight [[name("Append Weight")]];
    }
    
    if (bone_flag & 0x0400 != 0) {
        Vec3 fixed_axis [[name("Fixed Axis")]];
    }
    
    if (bone_flag & 0x0800 != 0) {
        Vec3 local_x_axis [[name("Local X Axis")]];
        Vec3 local_z_axis [[name("Local Z Axis")]];
    }
    
    if (bone_flag & 0x2000 != 0) {
        s32 key_value [[name("Key Value")]];
    }
    
    if (bone_flag & 0x0020 != 0) {
        PMXBoneIKs bone_iks [[name("Bone IK Data")]];
    }
};

struct PMXBones {
    s32 num [[name("Bone Count")]];
    PMXBone bones[num] [[name("Bones")]];
} [[static]];

enum PMXMorphType : u8 {
    Group = 0x00,
    Position = 0x01, 
    Bone = 0x02, 
    UV = 0x03, 
    AddUV1 = 0x04, 
    AddUV2 = 0x05, 
    AddUV3 = 0x06, 
    AddUV4 = 0x07, 
    Material = 0x08, 
    Flip = 0x09, 
    Impluse = 0x0a
};

struct PMXPositionMorphData {
    if (vertex_index_size == 1) s8 vertex_index [[name("Vertex Index")]];
    else if (vertex_index_size == 2) s16 vertex_index [[name("Vertex Index")]];
    else if (vertex_index_size == 4) s32 vertex_index [[name("Vertex Index")]];
    
    Vec3 pos [[name("Position")]];
};

struct PMXUVMorphData {
    if (vertex_index_size == 1) s8 vertex_index [[name("Vertex Index")]];
    else if (vertex_index_size == 2) s16 vertex_index [[name("Vertex Index")]];
    else if (vertex_index_size == 4) s32 vertex_index [[name("Vertex Index")]];
    
    Vec4 uv [[name("UV")]];
};

struct PMXBoneMorphData {
    if (bone_index_size == 1) s8 bone_index [[name("Bone Index")]];
    else if (bone_index_size == 2) s16 bone_index [[name("Bone Index")]];
    else if (bone_index_size == 4) s32 bone_index [[name("Bone Index")]];
    
    Vec3 pos [[name("Position")]];
    Quat quaternion [[name("Quaternion")]];
};

enum OpType : u8 {
    Mul = 0x00, 
    Add = 0x01
};

struct PMXMaterialMorphData {
    if (material_index_size == 1) s8 material_index [[name("Material Index")]];
    else if (material_index_size == 2) s16 material_index [[name("Material Index")]];
    else if (material_index_size == 4) s32 material_index [[name("Material Index")]];
    
    OpType op_type [[name("Operation Type")]];
    Vec4 diffuse [[name("Diffuse")]];
    Vec3 specular [[name("Specular")]];
    float specular_power [[name("Specular Power")]];
    Vec3 ambient [[name("Ambient")]];
    Vec4 edge_color [[name("Edge Color")]];
    float edge_size [[name("Edge Size")]];
    Vec4 texture_factor [[name("Texture Factor")]];
    Vec4 sphere_texture_factor [[name("Sphere Texture Factor")]];
    Vec4 toon_texture_factor [[name("Toon Texture Factor")]];
};

struct PMXGroupMorphData {
    if (morph_index_size == 1) s8 morph_index [[name("Morph Index")]];
    else if (morph_index_size == 2) s16 morph_index [[name("Morph Index")]];
    else if (morph_index_size == 4) s32 morph_index [[name("Morph Index")]];
    
    float weight [[name("weight")]];
};

struct PMXFlipMorphData {
    if (morph_index_size == 1) s8 morph_index [[name("Morph Index")]];
    else if (morph_index_size == 2) s16 morph_index [[name("Morph Index")]];
    else if (morph_index_size == 4) s32 morph_index [[name("Morph Index")]];
    
    float weight [[name("weight")]];
};

struct PMXImpluseMorphData {
    if (rigidbody_index_size == 1) s8 rigidbody_index [[name("Rigid Body Index")]];
    else if (rigidbody_index_size == 2) s16 rigidbody_index [[name("Rigid Body Index")]];
    else if (rigidbody_index_size == 4) s32 rigidbody_index [[name("Rigid Body Index")]];
    
    u8 local_flag [[name("Local Flag"), comment("0: OFF, 1: ON")]];
    Vec3 translate_velocity [[name("Translate Velocity")]];
    Vec3 rotate_torque [[name("Rotate Torque")]];
};

struct PMXMorph {
    String name [[name("Morph Name")]];
    String englishname [[name("Morph English Name")]];
    
    u8 control_panel [[name("Control Panel")]];
    PMXMorphType morph_type [[name("Morph Type")]];
    
    s32 data_count [[name("Data Count")]];
    
    if (morph_type == PMXMorphType::Position) PMXPositionMorphData position_morph[data_count] [[name("Position Morphs")]];
    else if (morph_type == PMXMorphType::Material) PMXMaterialMorphData material_morph[data_count] [[name("Material Morphs")]];
    else if (morph_type == PMXMorphType::UV || morph_type == PMXMorphType::AddUV1 || morph_type == PMXMorphType::AddUV2 || morph_type == PMXMorphType::AddUV3 || PMXMorphType::AddUV4) PMXUVMorphData uv_morph[data_count] [[name("UV Morphs")]];
    else if (morph_type == PMXMorphType::Bone) PMXBoneMorphData bone_morph[data_count] [[name("Bone Morphs")]];
    else if (morph_type == PMXMorphType::Group) PMXGroupMorphData group_morph[data_count] [[name("Group Morphs")]];
    else if (morph_type == PMXMorphType::Flip) PMXFlipMorphData flip_morph[data_count] [[name("Flip Morphs")]];
    else if (morph_type == PMXMorphType::Impluse) PMXImpluseMorphData impluse_morph[data_count] [[name("Impluse Morphs")]];
};

struct PMXMorphs {
    s32 num [[name("Morph Count")]];
    PMXMorph morphs[num] [[name("Morphs")]];
} [[static]];

enum FrameTypes : u8 {
    DefaultFrame = 0x00, 
    SpecialFrame = 0x01
};

enum PMXDisplayFrameTargetTypes : u8 {
    BoneIndex = 0x00, 
    MorphIndex = 0x01
};

struct PMXDisplayFrameTarget {
    PMXDisplayFrameTargetTypes target_type [[name("Target Type")]];
    if (target_type == PMXDisplayFrameTargetTypes::BoneIndex) {
        if (bone_index_size == 1) u8 bone_index [[name("Bone Index")]];
        else if (bone_index_size == 2) u16 bone_index [[name("Bone Index")]];
        else if (bone_index_size == 4) u32 bone_index [[name("Bone Index")]];
    }
    else if (target_type == PMXDisplayFrameTargetTypes::MorphIndex) {
        if (morph_index_size == 1) u8 morph_index [[name("Morph Index")]];
        else if (morph_index_size == 2) u16 morph_index [[name("Morph Index")]];
        else if (morph_index_size == 4) u32 morph_index [[name("Morph Index")]];
    }
};

struct PMXDisplayFrame {
    String name [[name("Display Frame Name")]];
    String englishname [[name("Display Frame English Name")]];
    FrameTypes frame_type [[name("Display Frame")]];
    s32 target_count [[name("Target Count")]];
    PMXDisplayFrameTarget targets[target_count] [[name("Targets")]];
};

struct PMXDisplayFrames {
    s32 num [[name("Display Frame Count")]];
    PMXDisplayFrame display_frames[num] [[name("Display Frames")]];
} [[static]];

enum PMXRigidBodyShape : u8 {
    Shpere = 0x00, 
    Box = 0x01, 
    Capsule = 0x02
};

enum PMXRigidBodyOperation : u8 {
    Static = 0x00, 
    Dynamic = 0x01, 
    DynamicAndBoneMerge = 0x02
};

struct PMXRigidBody {
    String name [[name("Rigid Body Name")]];
    String englishname [[name("Rigid Body English Name")]];
    
    if (bone_index_size == 1) u8 bone_index [[name("Bone Index")]];
    else if (bone_index_size == 2) u16 bone_index [[name("Bone Index")]];
    else if (bone_index_size == 4) u32 bone_index [[name("Bone Index")]];
    
    u8 group [[name("Group")]];
    u16 collision [[name("Collision Group")]];
    PMXRigidBodyShape shape [[name("Shape")]];
    Vec3 shape_size [[name("Shape size")]];
    
    Vec3 translate [[name("Translate")]];
    Vec3 rotate [[name("Rotate")]];
    
    float mass [[name("Mass")]];
    float translate_dimmer [[name("Translate Dimmer")]];
    float rotate_dimmer [[name("Rotate DImmer")]];
    float repulsion [[name("Repulsion")]];
    float friction [[name("Friction")]];
    
    PMXRigidBodyOperation operation [[name("Operation")]];
};

struct PMXRigidBodies {
    s32 num [[name("Rigid Body Count")]];
    PMXRigidBody rigidbodies[num] [[name("Rigid Bodies")]];
};

enum PMXJointTypes : u8 {
    SpringDOF6 = 0x00, 
    DOF6 = 0x01, 
    P2P = 0x02, 
    ConeTwist = 0x03, 
    Slider = 0x04, 
    Hinge = 0x05
};

struct PMXJoint {
    String name [[name("Joint Name")]];
    String englishname [[name("Joint English Name")]];
    
    PMXJointTypes type [[name("Type")]];
    if (rigidbody_index_size == 1) {
        u8 rigidbody_a_index [[name("Rigid Body A Index")]];
        u8 rigidbody_b_index [[name("Rigid Body B Index")]];
    }
    else if (rigidbody_index_size == 2) {
        u16 rigidbody_a_index [[name("Rigid Body A Index")]];
        u16 rigidbody_b_index [[name("Rigid Body B Index")]];
    }
    else if (rigidbody_index_size == 4) {
        u32 rigidbody_a_index [[name("Rigid Body A Index")]];
        u32 rigidbody_b_index [[name("Rigid Body B Index")]];
    }
    
    Vec3 translate [[name("Translate")]];
    Vec3 rotate [[name("Rotate")]];
    Vec3 translate_lower_limit [[name("Translate Lower Limit")]];
    Vec3 translate_upper_limit [[name("Translate Upper Limit")]];
    Vec3 rotate_lower_limit [[name("Rotate Lower Limit")]];
    Vec3 rotate_upper_limit [[name("Rotate Upper Limit")]];
    
    Vec3 spring_translate_factor [[name("Spring Translate Factor")]];
    Vec3 spring_rotate_factor [[name("Spring Rotate Factor")]];    
};

struct PMXJoints {
    s32 num [[name("Joint Count")]];
    PMXJoint joints[num] [[name("Joints")]];
};

struct PMXModel {
    PMXHeader header [[name("PMX Header")]];
    PMXInfo info [[name("PMX Info")]];
    PMXVertices vertices [[name("PMX Vertices")]];
    PMXFaces faces [[name("PMX Faces")]];
    PMXTextures textures [[name("PMX Textures")]];
    PMXMaterials materials [[name("PMX Materials")]];
    PMXBones bones [[name("PMX Bones")]];
    PMXMorphs morphs [[name("PMX Morphs")]];
    PMXDisplayFrames display_frames [[name("PMX Display Frames")]];
    PMXRigidBodies rigidbodies [[name("PMX Rigid Bodies")]];
    PMXJoints joints [[name("PMX Joints")]];
} [[static]];

PMXModel model @ 0x0 [[name("PMX Model")]];