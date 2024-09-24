#pragma once

namespace IL2CPP
{
	struct CallbackHook_t
	{
		std::vector<void*> m_Funcs;

		void** m_VFunc = nullptr;
		void* m_Original = nullptr;
	};

	namespace Callback
	{
		namespace OnUpdate
		{
			CallbackHook_t m_CallbackHook;

			void Add(void* m_pFunction) 
			{ 
				m_CallbackHook.m_Funcs.emplace_back(m_pFunction);
			}

			void Hook(void* rcx)
			{
				for (void* m_Func : m_CallbackHook.m_Funcs)
					reinterpret_cast<void(*)()>(m_Func)();

				reinterpret_cast<void(*)(void*)>(m_CallbackHook.m_Original)(rcx);
			}
		}

		namespace OnLateUpdate
		{
			CallbackHook_t m_CallbackHook;

			void Add(void* m_pFunction)
			{
				m_CallbackHook.m_Funcs.emplace_back(m_pFunction);
			}

			void Hook(void* rcx)
			{
				for (void* m_Func : m_CallbackHook.m_Funcs)
					reinterpret_cast<void(*)()>(m_Func)();

				reinterpret_cast<void(*)(void*)>(m_CallbackHook.m_Original)(rcx);
			}
		}

		void Initialize()
		{
			void* m_IL2CPPThread = Thread::Attach(IL2CPP::Domain::Get());

			// Find
			void** m_MonoBehaviourVTable = *reinterpret_cast<void***>(IL2CPP::Helper::GetMonoBehaviour()->m_CachedPtr);
			if (m_MonoBehaviourVTable)
			{
#ifdef __aarch64__
				OnUpdate::m_CallbackHook.m_VFunc		= Utils::VTable::FindFunction(m_MonoBehaviourVTable, 99, { 0x02, 0x00, 0x80, 0xD2, 0x00, 0x00, 0x00, 0x14 }); // mov x2, #0 | b
				OnLateUpdate::m_CallbackHook.m_VFunc	= Utils::VTable::FindFunction(m_MonoBehaviourVTable, 99, { 0x22, 0x00, 0x80, 0xD2, 0x00, 0x00, 0x00, 0x14 }); // mov x2, #1 | b
#elif __arm__
				OnUpdate::m_CallbackHook.m_VFunc		= Utils::VTable::FindFunction(m_MonoBehaviourVTable, 99, { 0x00, 0x20, 0xA0, 0xE3, 0x00, 0x00, 0x00, 0xEA }); // mov r2, #0 | b
				OnLateUpdate::m_CallbackHook.m_VFunc	= Utils::VTable::FindFunction(m_MonoBehaviourVTable, 99, { 0x01, 0x20, 0xA0, 0xE3, 0x00, 0x00, 0x00, 0xEA }); // mov r2, #1 | b
#endif
			}

			IL2CPP::Thread::Detach(m_IL2CPPThread);

			// Replace (Hook)
			Utils::VTable::ReplaceFunction(OnUpdate::m_CallbackHook.m_VFunc, (void*)OnUpdate::Hook, &OnUpdate::m_CallbackHook.m_Original);
			Utils::VTable::ReplaceFunction(OnLateUpdate::m_CallbackHook.m_VFunc, (void*)OnLateUpdate::Hook, &OnLateUpdate::m_CallbackHook.m_Original);
		}

		void Uninitialize()
		{
			Utils::VTable::ReplaceFunction(OnUpdate::m_CallbackHook.m_VFunc, OnUpdate::m_CallbackHook.m_Original);
			Utils::VTable::ReplaceFunction(OnLateUpdate::m_CallbackHook.m_VFunc, OnLateUpdate::m_CallbackHook.m_Original);
		}
	}
}