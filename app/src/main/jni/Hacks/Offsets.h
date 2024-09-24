//
// Created by rosetta on 31/05/2024.
//

namespace Offsets {
    struct CPlayersInventoryWeapon {
        const uint64_t ReloadSpeed = 0x21DCAE0; // WarPolygon.CPlayersInventoryWeapon.get_ReloadSpeed()
        const uint64_t Damage = 0x21DCA50; // WarPolygon.CPlayersInventoryWeapon.Damage(bool pIsSp)
        const uint64_t InfinityAmmo = 0x21DCB68; // WarPolygon.CPlayersInventoryWeapon.get_InfinityAmmo()
        const uint64_t MagazineSize = 0x21DCB0C; // WarPolygon.CPlayersInventoryWeapon.get_MagazineSize()
        const uint64_t InMagazine = 0x21DCB38; // WarPolygon.CPlayersInventoryWeapon.get_InMagazine()
        const uint64_t WeaponId = 0x21DCA98; // WarPolygon.CPlayersInventoryWeapon.get_Id()
    };
    CPlayersInventoryWeapon CPlayersInventoryWeapon;

    struct CPlayersConsumable {
        const uint64_t InfinityConsumable = 0x21DCE94; // WarPolygon.CPlayersConsumable.get_InfinityAmoo()
    };
    CPlayersConsumable CPlayersConsumable;

    struct CPlayerBase {
        const uint64_t Health = 0x22049D0; // WarPolygon.CPlayerBase.get_MaxHealth()
        const uint64_t BoostArmor = 0x2204B2C; // WarPolygon.CPlayerBase.get_BoostArmor()
        const uint64_t BoostRage = 0x2204C80; // WarPolygon.CPlayerBase.get_BoostRage()
        const uint64_t BoostPill = 0x2204DD4; // WarPolygon.CPlayerBase.get_BoostPill()
        const uint64_t BoostPanzerChocolate = 0x2204EAC; // WarPolygon.CPlayerBase.get_BoostPanzerChocholate()
        const uint64_t BoostShoKaCola = 0x21FC054; // WarPolygon.CPlayerBase.get_BoostShoKaCola()
        const uint64_t BoostChewingTobacco = 0x2204ED0; // WarPolygon.CPlayerBase.get_ChewingTobacco()
        const uint64_t Update = 0x2204EFC; // WarPolygon.CPlayerBase.Update()
    };
    CPlayerBase CPlayerBase;

    struct CPlayerLocal {
        const uint64_t MoveSpeedModificator = 0x21FC078; // WarPolygon.CPlayerLocal.get_MoveSpeedModificator()
    };
    CPlayerLocal CPlayerLocal;

    struct CPlayerWeaponBase {
        const uint64_t Update = 0x221692C; // WarPolygon.CPlayerWeaponBase.Update()
        const uint64_t NoSway = 0x22166B8; // WarPolygon.CPlayerWeaponBase.get_canSway()
        const uint64_t NoRecoil = 0x225A5C4; // WarPolygon.CPlayerWeaponBase.get_CurrentSpread()
        const uint64_t SwitchWeapon = 0x21FEC88; // WarPolygon.CPlayerWeaponBase.SwitchWeapon()
    };
    CPlayerWeaponBase CPlayerWeaponBase;

    struct CMainMenuController {
        const uint64_t Update = 0x2248C54; // WarPolygon.CMainMenuController.Update()
    };
    CMainMenuController CMainMenuController;

    struct CEnemyBossCaptain {
        // update
        const uint64_t Update = 0x21D675C; // WarPolygon.CEnemyBossCaptain.Update()
    };
    CEnemyBossCaptain CEnemyBossCaptain;


}
