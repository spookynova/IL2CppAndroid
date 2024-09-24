#pragma once

namespace IL2CPP
{
    namespace Utils
    {
        namespace VTable
        {
            void ReplaceFunction(void** m_VTableFunc, void* m_NewFunc, void** m_Original = nullptr)
            { // Researched code, I don't know if it works properly as VirtualProtect from WIN API
                if (!m_VTableFunc)
                    return;
                
                long pageSize = sysconf(_SC_PAGESIZE);
                
                uintptr_t pageStart = ((uintptr_t)m_VTableFunc) & ~(pageSize - 1);
                
                if (mprotect((void*)pageStart, pageSize, PROT_READ | PROT_WRITE) == 0)
                {
                    if (m_Original)
                        *m_Original = *m_VTableFunc;
                    *m_VTableFunc = m_NewFunc;
                
                    mprotect((void*)pageStart, pageSize, PROT_READ | PROT_EXEC);
                }
            }

            void** FindFunction(void** m_VTable, int m_Count, std::initializer_list<unsigned char> m_Opcodes)
            {
                size_t m_OpcodeSize = m_Opcodes.size();
                const void* m_OpcodesPtr = m_Opcodes.begin();

                for (int i = 0; m_Count > i; ++i)
                {
                    if (memcmp(m_VTable[i], m_OpcodesPtr, m_OpcodeSize) == 0)
                        return &m_VTable[i];
                }

                return 0;
            }
        }
    }
}