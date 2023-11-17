function ProfileItem({ profileInfo }) { //props로 내려준 정보를 꺼낸다.

    return (
        <div className="profile-item-div">
            <h1>내 정보</h1>
            <input
                type="text"
                readOnly={ true }
                value={ profileInfo.memberId }
            />
            <input
                type="text"
                readOnly={ true }
                value={ profileInfo.memberName }
            />
            <input
                type="text"
                readOnly={ true }
                value={ profileInfo.memberEmail }
            />
        </div>
    )



}

export default ProfileItem;